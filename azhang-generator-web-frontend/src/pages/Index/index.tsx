import { listGeneratorVoByPageUsingPost } from '@/services/backend/generatorController';
import { doGeneratorFavourUsingPost } from '@/services/backend/generatorFavourController';
import { doThumbUsingPost } from '@/services/backend/generatorThumbController';
import {
  DownOutlined,
  LikeFilled,
  LikeOutlined,
  StarFilled,
  StarOutlined,
  UpOutlined,
} from '@ant-design/icons';
import {
  PageContainer,
  ProFormSelect,
  ProFormText,
  ProList,
  QueryFilter,
} from '@ant-design/pro-components';
import { Link } from '@umijs/max';
import { Flex, Image, Input, message, Tabs, Tag } from 'antd';
import React, { useEffect, useState } from 'react';

const DEFAULT_PAGE_PARAMS: PageRequest = {
  current: 1,
  pageSize: 4,
  sortField: 'createTime',
  // @ts-ignore
  sortOrder: 'newest',
};

const IndexPage: React.FC = () => {
  const [loading, setLoading] = useState<boolean>(true);
  const [dataList, setDataList] = useState<API.GeneratorVO[]>([]);
  const [total, setTotal] = useState<number>(0);
  const [showFilter, setShowFilter] = useState<boolean>(true);

  const [searchParams, setSearchParams] = useState<API.GeneratorQueryRequest>({
    ...DEFAULT_PAGE_PARAMS,
  });

  const doSearch = async () => {
    setLoading(true);
    try {
      const res = await listGeneratorVoByPageUsingPost(searchParams);
      setDataList(res.data?.records ?? []);
      setTotal(res.data?.total ?? 0);
    } catch (error: any) {
      message.error('获取数据失败！', error.message);
      setTimeout(() => {
        message.destroy(); // 关闭所有消息提示
      }, 3000);
    }
    setLoading(false);
  };

  useEffect(() => {
    doSearch();
  }, [searchParams]);

  const IconText = ({ icon, text, onClick }: { icon: any; text: string; onClick?: () => void }) => (
    <span onClick={onClick}>
      {React.createElement(icon, { style: { marginInlineEnd: 8 } })}
      {text}
    </span>
  );

  const doThumb = async (req: API.GeneratorThumbAddRequest) => {
    setLoading(true);
    try {
      const res = await doThumbUsingPost(req);
      if (res.code === 0) {
        message.success(res.data === 1 ? '点赞成功！' : '取消点赞！');
        setSearchParams({
          ...searchParams,
        });
      }
    } catch (error: any) {
      message.error('失败！', error.message);
    }
    setTimeout(() => {
      message.destroy(); // 关闭所有消息提示
    }, 3000);
    setLoading(false);
  };

  const doFavour = async (req: API.GeneratorFavourAddRequest) => {
    setLoading(true);
    try {
      const res = await doGeneratorFavourUsingPost(req);
      if (res.code === 0) {
        message.success(res.data === 1 ? '收藏成功！' : '取消收藏！');
        setSearchParams({
          ...searchParams,
        });
      }
    } catch (error: any) {
      message.error('失败！', error.message);
    }
    setTimeout(() => {
      message.destroy(); // 关闭所有消息提示
    }, 3000);
    setLoading(false);
  };

  return (
    <PageContainer title={<></>}>
      <Flex justify="center">
        <Input.Search
          placeholder="请输入想要查找的生成器"
          allowClear
          style={{ width: '40vw', minWidth: '233px' }}
          enterButton="搜索"
          size="large"
          onChange={(e) => {
            searchParams.searchText = e.target.value;
          }}
          onSearch={(value: string) => {
            setSearchParams({
              ...DEFAULT_PAGE_PARAMS,
              searchText: value,
            });
          }}
        />
      </Flex>
      <div style={{ marginBottom: 12 }}></div>
      <Tabs
        defaultActiveKey="newest"
        onChange={(e) => {
          if (e === 'newest') {
            setSearchParams({
              ...searchParams,
              sortOrder: e,
              sortField: 'createTime',
            });
          } else {
            setSearchParams({
              ...searchParams,
              sortOrder: e,
              sortField: 'thumbNum',
            });
          }
        }}
        tabBarExtraContent={
          <a
            style={{
              display: 'flex',
              gap: 4,
            }}
            onClick={() => {
              setShowFilter(!showFilter);
            }}
          >
            高级筛选 {showFilter ? <UpOutlined /> : <DownOutlined />}
          </a>
        }
        items={[
          {
            key: 'newest',
            label: '最新',
          },
          {
            key: 'recommend',
            label: '推荐',
          },
        ]}
      />
      {showFilter ? (
        <QueryFilter
          span={12}
          labelWidth="auto"
          split
          onFinish={async (values: API.GeneratorQueryRequest) => {
            setSearchParams({
              ...DEFAULT_PAGE_PARAMS,
              searchText: searchParams.searchText,
              ...values,
            });
          }}
        >
          <ProFormSelect label="标签" name="tags" mode="tags" />
          <ProFormText label="名称" name="name" />
          <ProFormText label="描述" name="description" />
        </QueryFilter>
      ) : null}

      <div style={{ marginBottom: 12 }}></div>
      <ProList<API.GeneratorVO>
        style={{ cursor: 'pointer' }}
        pagination={{
          pageSize: searchParams.pageSize,
          current: searchParams.current,
          total,
          onChange(current, pageSize) {
            setSearchParams({
              ...searchParams,
              current,
              pageSize,
            });
          },
        }}
        itemLayout="vertical"
        rowKey="id"
        headerTitle=" "
        dataSource={dataList}
        metas={{
          title: {
            render: (_, entity) => {
              return (
                <div style={{ paddingLeft: '10px' }}>
                  <Link
                    style={{ color: '#333', fontSize: 18 }}
                    to={`/generator/detail/${entity.id}`}
                  >
                    {entity.name}
                  </Link>
                </div>
              );
            },
          },
          description: {
            render: (_, entity) => {
              if (!entity.tags) {
                return <></>;
              }
              return (
                <div style={{ paddingLeft: '10px' }}>
                  {entity.tags.map((item, index) => {
                    return <Tag key={index}>{item}</Tag>;
                  })}
                </div>
              );
            },
          },
          actions: {
            render: (_, entity) => (
              <div
                style={{
                  paddingLeft: '10px',
                  marginTop: '15px',
                  display: 'flex',
                  gap: '12px',
                  cursor: 'pointer',
                }}
              >
                <IconText
                  icon={entity.hasThumb ? LikeFilled : LikeOutlined}
                  // @ts-ignore
                  text={entity.thumbNum}
                  key="list-vertical-like-o"
                  onClick={() => {
                    doThumb({
                      generatorId: entity.id,
                    });
                  }}
                />
                <IconText
                  icon={entity.hasFavour ? StarFilled : StarOutlined}
                  // @ts-ignore
                  text={entity.favourNum}
                  key="list-vertical-star-o"
                  onClick={() => {
                    doFavour({
                      generatorId: entity.id,
                    });
                  }}
                />
              </div>
            ),
          },
          extra: {
            render: (_: any, entity: { name: string | undefined; picture: string | undefined }) => {
              return (
                <div style={{ paddingRight: '10px' }}>
                  {<Image width={220} height={180} alt={entity.name} src={entity.picture} />}
                </div>
              );
            },
          },
          content: {
            render: (_, entity) => {
              return <div>{entity.description}</div>;
            },
          },
        }}
      />
    </PageContainer>
  );
};

// @ts-ignore
export default IndexPage;
