//package com.azhang.web.job.cycle;
//
//import cn.hutool.core.collection.CollUtil;
//import com.azhang.web.esdao.PostEsDao;
//import com.azhang.web.mapper.PostMapper;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.scheduling.annotation.Scheduled;
//
//import javax.annotation.Resource;
//import java.util.Date;
//import java.util.List;
//import java.util.stream.Collectors;
//
///**
// * 增量同步帖子到 es
// *
// * @author codeZhang
// */
//// todo 取消注释开启任务
////@Component
//@Slf4j
//public class IncSyncPostToEs {
//
//    @Resource
//    private PostMapper postMapper;
//
//    /**
//     * 每分钟执行一次
//     */
//    @Scheduled(fixedRate = 60 * 1000)
//    public void run() {
//        // 查询近 5 分钟内的数据
//        Date fiveMinutesAgoDate = new Date(new Date().getTime() - 5 * 60 * 1000L);
//        List<Post> postList = postMapper.listPostWithDelete(fiveMinutesAgoDate);
//        if (CollUtil.isEmpty(postList)) {
//            log.info("no inc post");
//            return;
//        }
//        List<PostEsDTO> postEsDTOList = postList.stream()
//                .map(PostEsDTO::objToDto)
//                .collect(Collectors.toList());
//        final int pageSize = 500;
//        int total = postEsDTOList.size();
//        log.info("IncSyncPostToEs start, total {}", total);
//        for (int i = 0; i < total; i += pageSize) {
//            int end = Math.min(i + pageSize, total);
//            log.info("sync from {} to {}", i, end);
//            postEsDao.saveAll(postEsDTOList.subList(i, end));
//        }
//        log.info("IncSyncPostToEs end, total {}", total);
//    }
//}
