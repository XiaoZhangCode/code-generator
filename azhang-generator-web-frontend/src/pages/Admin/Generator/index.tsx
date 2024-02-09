import CreateModal from '@/pages/Admin/Generator/components/CreateModal';
import UpdateModal from '@/pages/Admin/Generator/components/UpdateModal';
import {deleteGeneratorUsingPost, listGeneratorByPageUsingPost,} from '@/services/backend/generatorController';
import {PlusOutlined} from '@ant-design/icons';
import {ActionType, ProColumns} from '@ant-design/pro-components';
import {ProTable} from '@ant-design/pro-components';
import '@umijs/max';
import {Button, Input, message, Select, Space, Tag, Typography} from 'antd';
import React, {useRef, useState} from 'react';
import ProField from '@ant-design/pro-field';

/**
 * 代码生成器管理页面
 *
 * @constructor
 */
const GeneratorAdminPage: React.FC = () => {
  // 是否显示新建窗口
  const [createModalVisible, setCreateModalVisible] = useState<boolean>(false);
  // 是否显示更新窗口
  const [updateModalVisible, setUpdateModalVisible] = useState<boolean>(false);
  const actionRef = useRef<ActionType>();
  // 当前代码生成器点击的数据
  const [currentRow, setCurrentRow] = useState<API.Generator>();

  /**
   * 删除节点
   *
   * @param row
   */
  const handleDelete = async (row: API.Generator) => {
    const hide = message.loading('正在删除');
    if (!row) return true;
    try {
      await deleteGeneratorUsingPost({
        id: row.id as any,
      });
      hide();
      message.success('删除成功');
      actionRef?.current?.reload();
      return true;
    } catch (error: any) {
      hide();
      message.error('删除失败，' + error.message);
      return false;
    }
  };

  /**
   * 表格列配置
   */
  const columns: ProColumns<API.Generator>[] = [
    {
      title: 'id',
      dataIndex: 'id',
      valueType: 'text',
      hideInForm: true,
    },
    {
      title: '生成器名称',
      dataIndex: 'name',
      valueType: 'text',
    },
    {
      title: '描述',
      dataIndex: 'description',
      valueType: 'textarea',
      ellipsis: true,
      hideInSearch: true,
    },
    {
      title: '作者',
      dataIndex: 'author',
      valueType: 'text',
    },
    {
      title: '版本',
      dataIndex: 'version',
      valueType: 'text',
      hideInSearch: true,
    },
    {
      title: '标签',
      dataIndex: 'tags',
      valueType: 'select',
      renderFormItem: (schema) => {
        const { fieldProps } = schema;
        // @ts-ignore
        return <Select {...fieldProps} mode="tags" variant="outlined" />;
      },
      render: (_, record) => {
        if (!record.tags) {
          return <></>;
        }
        return (
          <Space>
            {record.tags.map((name: string) => (
              <Tag key={name}>{name}</Tag>
            ))}
          </Space>
        );
      },
    },
    {
      title: '图片',
      dataIndex: 'picture',
      valueType: 'image',
      fieldProps: {
        width: 64,
      },
      hideInSearch: true,
    },
    {
      title: '包名',
      dataIndex: 'basePackage',
      valueType: 'text',
      hideInSearch: true,
    },
    {
      title: '版本控制',
      dataIndex: 'versionControl',
      valueType: 'switch',
      hideInSearch: true,
      initialValue: true,
      valueEnum: {
        true: {
          text: '开启',
        },
        false: {
          text: '关闭',
        },
      },
      render: (_, record) => {
        return record.versionControl ? '开启' : '关闭'; // 根据 versionControl 字段的值进行渲染
      },
    },
    {
      title: '强制交互',
      dataIndex: 'forcedInteractiveSwitch',
      valueType: 'switch',
      hideInSearch: true,
      initialValue: true,
      valueEnum: {
        true: {
          text: '开启',
        },
        false: {
          text: '关闭',
        },
      },
      render: (_, record) => {
        return record.forcedInteractiveSwitch ? '开启' : '关闭'; // 根据 versionControl 字段的值进行渲染
      },
    },
    {
      title: '文件配置',
      dataIndex: 'fileConfig',
      hideInSearch: true,
      valueType: 'jsonCode',
      hideInTable: true,
      render(_, record) {
        if (!record.fileConfig) {
          return '{}';
        }
        return <ProField text={JSON.stringify(record.fileConfig)} mode="read" valueType="jsonCode" />;
      }
    },
    {
      title: '模型配置',
      dataIndex: 'modelConfig',
      valueType: 'jsonCode',
      hideInSearch: true,
      hideInTable: true,
      render(_, record) {
        if (!record.modelConfig) {
          return '{}';
        }
        return <ProField text={JSON.stringify(record.modelConfig)} mode="read" valueType="jsonCode" />;
      }
    },
    {
      title: '产物路径',
      dataIndex: 'distPath',
      valueType: 'text',
      hideInSearch: true,
      hideInForm: true,
    },
    {
      title: '状态',
      dataIndex: 'status',
      valueType: 'select',
      hideInForm: true,
      valueEnum: {
        0: {
          text: '默认',
        },
      },
    },
    {
      title: '点赞',
      dataIndex: 'thumbNum',
      valueType: 'text',
      hideInSearch: true,
      hideInForm: true,
    },
    {
      title: '收藏',
      dataIndex: 'favourNum',
      valueType: 'text',
      hideInSearch: true,
      hideInForm: true,
    },
    {
      title: '创建时间',
      sorter: true,
      dataIndex: 'createTime',
      valueType: 'dateTime',
      hideInSearch: true,
      hideInForm: true,
    },
    {
      title: '更新时间',
      sorter: true,
      dataIndex: 'updateTime',
      valueType: 'dateTime',
      hideInSearch: true,
      hideInForm: true,
    },
    {
      title: '操作',
      dataIndex: 'option',
      valueType: 'option',
      render: (_, record) => (
        <Space size="middle">
          <Typography.Link
            onClick={() => {
              setCurrentRow(record);
              setUpdateModalVisible(true);
            }}
          >
            修改
          </Typography.Link>
          <Typography.Link type="danger" onClick={() => handleDelete(record)}>
            删除
          </Typography.Link>
        </Space>
      ),
    },
  ];

  return (
    <div className={'generator-admin-page'}>
      <Typography.Title level={4} style={{ marginBottom: 16 }}>
        生成器管理
      </Typography.Title>
      <ProTable<API.Generator>
        headerTitle={'查询表格'}
        actionRef={actionRef}
        rowKey="id"
        search={{
          labelWidth: 120,
        }}
        toolBarRender={() => [
          <Button
            type="primary"
            key="primary"
            onClick={() => {
              setCreateModalVisible(true);
            }}
          >
            <PlusOutlined /> 新建
          </Button>,
        ]}
        request={async (params, sort, filter) => {
          const sortField = Object.keys(sort)?.[0];
          const sortOrder = sort?.[sortField] ?? undefined;

          const { data, code } = await listGeneratorByPageUsingPost({
            ...params,
            sortField,
            sortOrder,
            ...filter,
          } as API.GeneratorQueryRequest);

          return {
            success: code === 0,
            data: data?.records || [],
            total: Number(data?.total) || 0,
          };
        }}
        columns={columns}
      />
      <CreateModal
        visible={createModalVisible}
        columns={columns}
        onSubmit={() => {
          setCreateModalVisible(false);
          actionRef.current?.reload();
        }}
        onCancel={() => {
          setCreateModalVisible(false);
        }}
      />
      <UpdateModal
        visible={updateModalVisible}
        columns={columns}
        oldData={currentRow}
        onSubmit={() => {
          setUpdateModalVisible(false);
          setCurrentRow(undefined);
          actionRef.current?.reload();
        }}
        onCancel={() => {
          setUpdateModalVisible(false);
        }}
      />
    </div>
  );
};
export default GeneratorAdminPage;
