package com.azhang.web.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import cn.hutool.json.JSONUtil;
import com.azhang.web.annotation.AuthCheck;
import com.azhang.web.common.BaseResponse;
import com.azhang.web.common.DeleteRequest;
import com.azhang.web.common.ErrorCode;
import com.azhang.web.common.ResultUtils;
import com.azhang.web.constant.UserConstant;
import com.azhang.web.exception.BusinessException;
import com.azhang.web.exception.ThrowUtils;
import com.azhang.web.manager.CosManager;
import com.azhang.web.mapstruct.GeneratorConvert;
import com.azhang.web.model.dto.generator.*;
import com.azhang.web.model.entity.Generator;
import com.azhang.web.model.entity.User;
import com.azhang.web.model.vo.GeneratorVO;
import com.azhang.web.service.GeneratorService;
import com.azhang.web.service.UserService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * 代码生成器接口
 *
 * @author codeZhang
 */
@RestController
@RequestMapping("/generator")
@Slf4j
public class GeneratorController {

    @Resource
    private GeneratorService generatorService;

    @Resource
    private UserService userService;

    @Resource
    private CosManager cosManager;


    /**
     * 创建
     *
     * @param generatorAddRequest 生成器参数
     * @param request             请求
     * @return 生成器id
     */
    @PostMapping("/add")
    public BaseResponse<Long> addGenerator(@RequestBody GeneratorAddRequest generatorAddRequest, HttpServletRequest request) {
        if (generatorAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Generator generator = GeneratorConvert.INSTANCE.convertGeneratorByAddRequest(generatorAddRequest);
        generatorService.validGenerator(generator, true);
        User loginUser = userService.getLoginUser(request);
        generator.setUserId(loginUser.getId());
        generator.setFavourNum(0);
        generator.setThumbNum(0);
        boolean result = generatorService.save(generator);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        long newGeneratorId = generator.getId();
        return ResultUtils.success(newGeneratorId);
    }

    /**
     * 删除
     *
     * @param deleteRequest 删除参数
     * @param request       请求
     * @return 删除结果
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteGenerator(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        Generator oldGenerator = generatorService.getById(id);
        ThrowUtils.throwIf(oldGenerator == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可删除
        if (!oldGenerator.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = generatorService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新（仅管理员）
     *
     * @param generatorUpdateRequest 更新参数
     * @return 更新结果
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateGenerator(@RequestBody GeneratorUpdateRequest generatorUpdateRequest) {
        if (generatorUpdateRequest == null || generatorUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Generator generator = GeneratorConvert.INSTANCE.convertGeneratorByUpdateRequest(generatorUpdateRequest);
        // 参数校验
        generatorService.validGenerator(generator, false);
        long id = generatorUpdateRequest.getId();
        // 判断是否存在
        Generator oldGenerator = generatorService.getById(id);
        ThrowUtils.throwIf(oldGenerator == null, ErrorCode.NOT_FOUND_ERROR);
        boolean result = generatorService.updateById(generator);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id id
     * @return 生成器
     */
    @GetMapping("/get/vo")
    public BaseResponse<GeneratorVO> getGeneratorVOById(long id, HttpServletRequest request) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Generator generator = generatorService.getById(id);
        if (generator == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        return ResultUtils.success(generatorService.getGeneratorVO(generator, request));
    }

    /**
     * 分页获取列表（仅管理员）
     *
     * @param generatorQueryRequest 查询参数
     * @return 分页结果
     */
    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<Generator>> listGeneratorByPage(@RequestBody GeneratorQueryRequest generatorQueryRequest) {
        long current = generatorQueryRequest.getCurrent();
        long size = generatorQueryRequest.getPageSize();
        Page<Generator> generatorPage = generatorService.page(new Page<>(current, size),
                generatorService.getQueryWrapper(generatorQueryRequest));
        return ResultUtils.success(generatorPage);
    }

    /**
     * 分页获取列表（封装类）
     *
     * @param generatorQueryRequest 查询参数
     * @param request               请求
     * @return 分页结果
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<GeneratorVO>> listGeneratorVOByPage(@RequestBody GeneratorQueryRequest generatorQueryRequest,
                                                                 HttpServletRequest request) {
        long current = generatorQueryRequest.getCurrent();
        long size = generatorQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<Generator> generatorPage = generatorService.page(new Page<>(current, size),
                generatorService.getQueryWrapper(generatorQueryRequest));
        return ResultUtils.success(generatorService.getGeneratorVOPage(generatorPage, request));
    }

    /**
     * 分页获取当前用户创建的资源列表
     *
     * @param generatorQueryRequest 查询参数
     * @param request               请求
     * @return 分页结果
     */
    @PostMapping("/my/list/page/vo")
    public BaseResponse<Page<GeneratorVO>> listMyGeneratorVOByPage(@RequestBody GeneratorQueryRequest generatorQueryRequest,
                                                                   HttpServletRequest request) {
        if (generatorQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        generatorQueryRequest.setUserId(loginUser.getId());
        long current = generatorQueryRequest.getCurrent();
        long size = generatorQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<Generator> generatorPage = generatorService.page(new Page<>(current, size),
                generatorService.getQueryWrapper(generatorQueryRequest));
        return ResultUtils.success(generatorService.getGeneratorVOPage(generatorPage, request));
    }


    /**
     * 编辑（用户）
     *
     * @param generatorEditRequest 编辑参数
     * @param request              请求
     * @return 编辑结果
     */
    @PostMapping("/edit")
    public BaseResponse<Boolean> editGenerator(@RequestBody GeneratorEditRequest generatorEditRequest, HttpServletRequest request) {
        if (generatorEditRequest == null || generatorEditRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Generator generator = GeneratorConvert.INSTANCE.convertGeneratorByEditRequest(generatorEditRequest);
        // 参数校验
        generatorService.validGenerator(generator, false);
        User loginUser = userService.getLoginUser(request);
        long id = generatorEditRequest.getId();
        // 判断是否存在
        Generator oldGenerator = generatorService.getById(id);
        ThrowUtils.throwIf(oldGenerator == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可编辑
        if (!oldGenerator.getUserId().equals(loginUser.getId()) && !userService.isAdmin(loginUser)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean result = generatorService.updateById(generator);
        return ResultUtils.success(result);
    }

    /**
     * 在线使用代码生成器
     *
     * @param generatorUseRequest 请求参数
     * @param request             请求对象上下文
     */
    @PostMapping("/useGenerator")
    public void useGenerator(@RequestBody GeneratorUseRequest generatorUseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 1. 获取请求参数
        ThrowUtils.throwIf(generatorUseRequest == null, ErrorCode.PARAMS_ERROR);
        User loginUser = userService.getLoginUser(request);
        ThrowUtils.throwIf(ObjectUtil.isEmpty(loginUser), ErrorCode.PARAMS_ERROR);
        Long id = generatorUseRequest.getId();
        Map<String, Object> dataModel = generatorUseRequest.getDataModel();

        // 2. 获取生成生成器的制作工具的产物包的路径
        Generator generator = generatorService.getById(id);
        ThrowUtils.throwIf(generator == null, ErrorCode.NOT_FOUND_ERROR);
        ThrowUtils.throwIf(StrUtil.isBlank(generator.getDistPath()), ErrorCode.PARAMS_ERROR);

        // 3. 下载产物包到本地解压
        // 3.1 定义一个临时的工作空间 用户存放下载的制作工具产物包和最终生成的代码文件
        String projectPath = System.getProperty("user.dir");
        String tempPath = String.format("%s/.temp/use/%s", projectPath, generator.getId());
        // 下载的的制作工具的产物包的名称
        String zipName = FileUtil.normalize(tempPath +File.separator +generator.getDistPath().substring(generator.getDistPath().lastIndexOf("/") + 1)) ;

        if (!FileUtil.exist(zipName)) {
            FileUtil.touch(zipName);
        }

        try {
            cosManager.download(generator.getDistPath(), zipName);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "代码生成器下载失败！");
        }

        // 4. 操作解压后的文件夹 调用脚本文件 得到生成的代码

        // 解压文件 获取到文件夹路径
        File unzipDistDir = ZipUtil.unzip(zipName, tempPath);
        // 将用户请求参数写入到临时目录json中
        String jsonStr = JSONUtil.toJsonStr(dataModel);
        String dataModelPath = FileUtil.normalize(tempPath + "/dataModel.json");
        FileUtil.writeUtf8String(jsonStr, dataModelPath);


        // 获取脚本文件
        File scriptFile = FileUtil.loopFiles(unzipDistDir, 2,
                        file -> file.isFile() && "generator.bat".equals(file.getName()))
                .stream()
                .findFirst()
                .orElseThrow(RuntimeException::new);
        // 添加可执行权限
        try {
            Set<PosixFilePermission> permissions = PosixFilePermissions.fromString("rwxrwxrwx");
            Files.setPosixFilePermissions(scriptFile.toPath(), permissions);
        } catch (Exception e) {}

        // 构造命令
        File scriptDir = scriptFile.getParentFile();
        String scriptFileAbsolutePath = FileUtil.normalize(scriptFile.getAbsolutePath());

        String[] commands = new String[]{scriptFileAbsolutePath,"json-generate","--file=" + dataModelPath};
        ProcessBuilder processBuilder = new ProcessBuilder(commands);
        processBuilder.directory(scriptDir);
        // 执行脚本命令
        try {
            Process process = processBuilder.start();

            // 读取命令的输出
            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // 等待命令执行完成
            int exitCode = process.waitFor();
            System.out.println("命令执行结束，退出码：" + exitCode);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "执行生成器脚本错误");
        }
        // 5. 后端将代码返回给用户下载
        // 压缩得到的生成结果
        String generatedPath = scriptDir.getAbsolutePath() + "/generated";
        String resultPath = tempPath + "/result.zip";
        File resultFile = ZipUtil.zip(generatedPath, resultPath);
        // 设置响应头
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + resultFile.getName());
        Files.copy(resultFile.toPath(), response.getOutputStream());

        // 6. 清除下载的资源 防止磁盘满溢
        CompletableFuture.runAsync(() -> {
            FileUtil.del(tempPath);
        });
    }

}
