declare namespace API {
  type BaseResponseBoolean_ = {
    code?: number;
    data?: boolean;
    message?: string;
  };

  type BaseResponseGeneratorVO_ = {
    code?: number;
    data?: GeneratorVO;
    message?: string;
  };

  type BaseResponseInt_ = {
    code?: number;
    data?: number;
    message?: string;
  };

  type BaseResponseLoginUserVO_ = {
    code?: number;
    data?: LoginUserVO;
    message?: string;
  };

  type BaseResponseLong_ = {
    code?: number;
    data?: number;
    message?: string;
  };

  type BaseResponsePageGenerator_ = {
    code?: number;
    data?: PageGenerator_;
    message?: string;
  };

  type BaseResponsePageGeneratorVO_ = {
    code?: number;
    data?: PageGeneratorVO_;
    message?: string;
  };

  type BaseResponsePageUser_ = {
    code?: number;
    data?: PageUser_;
    message?: string;
  };

  type BaseResponsePageUserVO_ = {
    code?: number;
    data?: PageUserVO_;
    message?: string;
  };

  type BaseResponseString_ = {
    code?: number;
    data?: string;
    message?: string;
  };

  type BaseResponseUser_ = {
    code?: number;
    data?: User;
    message?: string;
  };

  type BaseResponseUserVO_ = {
    code?: number;
    data?: UserVO;
    message?: string;
  };

  type cacheGeneratorUsingGETParams = {
    /** id */
    id?: number;
  };

  type DeleteRequest = {
    id?: number;
  };

  type downloadGeneratorByIdUsingGETParams = {
    /** id */
    id?: number;
  };

  type EmailVo = {
    userEmail?: string;
  };

  type FileConfigDTO = {
    files?: FileInfoDTO[];
    inputRootPath?: string;
    outputRootPath?: string;
    sourceRootPath?: string;
    type?: string;
  };

  type FileInfoDTO = {
    condition?: string;
    files?: FileInfoDTO[];
    generateType?: string;
    groupKey?: string;
    groupName?: string;
    inputPath?: string;
    outputPath?: string;
    type?: string;
  };

  type Generator = {
    author?: string;
    basePackage?: string;
    createTime?: string;
    description?: string;
    distPath?: string;
    favourNum?: number;
    fileConfig?: FileConfigDTO;
    forcedInteractiveSwitch?: boolean;
    id?: number;
    isDelete?: number;
    modelConfig?: ModelConfig;
    name?: string;
    picture?: string;
    status?: number;
    tags?: string[];
    thumbNum?: number;
    updateTime?: string;
    userId?: number;
    version?: string;
    versionControl?: boolean;
  };

  type GeneratorAddRequest = {
    author?: string;
    basePackage?: string;
    description?: string;
    distPath?: string;
    fileConfig?: FileConfigDTO;
    forcedInteractiveSwitch?: boolean;
    modelConfig?: ModelConfig;
    name?: string;
    picture?: string;
    tags?: string[];
    version?: string;
    versionControl?: boolean;
  };

  type GeneratorEditRequest = {
    author?: string;
    basePackage?: string;
    description?: string;
    distPath?: string;
    fileConfig?: FileConfigDTO;
    forcedInteractiveSwitch?: boolean;
    id?: number;
    modelConfig?: ModelConfig;
    name?: string;
    picture?: string;
    tags?: string[];
    version?: string;
    versionControl?: boolean;
  };

  type GeneratorFavourAddRequest = {
    generatorId?: number;
  };

  type GeneratorFavourQueryRequest = {
    current?: number;
    generatorQueryRequest?: GeneratorQueryRequest;
    pageSize?: number;
    sortField?: string;
    sortOrder?: string;
    userId?: number;
  };

  type GeneratorMakeRequest = {
    meta?: Meta;
    zipFilePath?: string;
  };

  type GeneratorQueryRequest = {
    content?: string;
    current?: number;
    id?: number;
    orTags?: string[];
    pageSize?: number;
    searchText?: string;
    sortField?: string;
    sortOrder?: string;
    tags?: string[];
    title?: string;
    userId?: number;
  };

  type GeneratorThumbAddRequest = {
    generatorId?: number;
  };

  type GeneratorUpdateRequest = {
    author?: string;
    basePackage?: string;
    description?: string;
    distPath?: string;
    fileConfig?: FileConfigDTO;
    forcedInteractiveSwitch?: boolean;
    id?: number;
    modelConfig?: ModelConfig;
    name?: string;
    picture?: string;
    status?: number;
    tags?: string[];
    version?: string;
    versionControl?: boolean;
  };

  type GeneratorUseRequest = {
    dataModel?: Record<string, any>;
    id?: number;
  };

  type GeneratorVO = {
    author?: string;
    basePackage?: string;
    createTime?: string;
    description?: string;
    distPath?: string;
    favourNum?: number;
    fileConfig?: FileConfigDTO;
    forcedInteractiveSwitch?: boolean;
    hasFavour?: boolean;
    hasThumb?: boolean;
    id?: number;
    modelConfig?: ModelConfig;
    name?: string;
    picture?: string;
    status?: number;
    tags?: string[];
    thumbNum?: number;
    updateTime?: string;
    user?: UserVO;
    version?: string;
    versionControl?: boolean;
  };

  type getGeneratorVOByIdUsingGETParams = {
    /** id */
    id?: number;
  };

  type getUserByIdUsingGETParams = {
    /** id */
    id?: number;
  };

  type getUserVOByIdUsingGETParams = {
    /** id */
    id?: number;
  };

  type LoginUserVO = {
    createTime?: string;
    id?: number;
    updateTime?: string;
    userAvatar?: string;
    userName?: string;
    userProfile?: string;
    userRole?: string;
  };

  type Meta = {
    author?: string;
    basePackage?: string;
    createTime?: string;
    description?: string;
    fileConfig?: FileConfigDTO;
    forcedInteractiveSwitch?: boolean;
    modelConfig?: ModelConfig;
    name?: string;
    version?: string;
    versionControl?: boolean;
  };

  type ModelConfig = {
    models?: ModelInfo[];
  };

  type ModelInfo = {
    abbr?: string;
    allArgsStr?: string;
    condition?: string;
    defaultValue?: Record<string, any>;
    description?: string;
    fieldName?: string;
    groupKey?: string;
    groupName?: string;
    models?: ModelInfo[];
    type?: string;
  };

  type OrderItem = {
    asc?: boolean;
    column?: string;
  };

  type PageGenerator_ = {
    countId?: string;
    current?: number;
    maxLimit?: number;
    optimizeCountSql?: boolean;
    orders?: OrderItem[];
    pages?: number;
    records?: Generator[];
    searchCount?: boolean;
    size?: number;
    total?: number;
  };

  type PageGeneratorVO_ = {
    countId?: string;
    current?: number;
    maxLimit?: number;
    optimizeCountSql?: boolean;
    orders?: OrderItem[];
    pages?: number;
    records?: GeneratorVO[];
    searchCount?: boolean;
    size?: number;
    total?: number;
  };

  type PageUser_ = {
    countId?: string;
    current?: number;
    maxLimit?: number;
    optimizeCountSql?: boolean;
    orders?: OrderItem[];
    pages?: number;
    records?: User[];
    searchCount?: boolean;
    size?: number;
    total?: number;
  };

  type PageUserVO_ = {
    countId?: string;
    current?: number;
    maxLimit?: number;
    optimizeCountSql?: boolean;
    orders?: OrderItem[];
    pages?: number;
    records?: UserVO[];
    searchCount?: boolean;
    size?: number;
    total?: number;
  };

  type testDownloadFileUsingGETParams = {
    /** filepath */
    filepath?: string;
  };

  type uploadFileUsingPOSTParams = {
    biz?: string;
  };

  type User = {
    createTime?: string;
    id?: number;
    isDelete?: number;
    updateTime?: string;
    userAccount?: string;
    userAvatar?: string;
    userName?: string;
    userPassword?: string;
    userProfile?: string;
    userRole?: string;
  };

  type UserAddRequest = {
    userAccount?: string;
    userAvatar?: string;
    userName?: string;
    userRole?: string;
  };

  type UserLoginRequest = {
    userAccount?: string;
    userPassword?: string;
  };

  type UserQueryRequest = {
    current?: number;
    id?: number;
    pageSize?: number;
    sortField?: string;
    sortOrder?: string;
    userName?: string;
    userProfile?: string;
    userRole?: string;
  };

  type UserRegisterRequest = {
    checkPassword?: string;
    code?: string;
    userAccount?: string;
    userEmail?: string;
    userPassword?: string;
  };

  type UserUpdateMyRequest = {
    userAvatar?: string;
    userName?: string;
    userProfile?: string;
  };

  type UserUpdateRequest = {
    id?: number;
    userAvatar?: string;
    userName?: string;
    userProfile?: string;
    userRole?: string;
  };

  type UserVO = {
    createTime?: string;
    id?: number;
    userAvatar?: string;
    userName?: string;
    userProfile?: string;
    userRole?: string;
  };
}
