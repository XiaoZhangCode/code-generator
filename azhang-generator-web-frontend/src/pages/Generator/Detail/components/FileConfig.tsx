import { FileOutlined, InfoCircleOutlined } from '@ant-design/icons';
import { Descriptions, DescriptionsProps, Divider } from 'antd';
import React from 'react';

interface Props {
  data: API.GeneratorVO;
}

/**
 * 文件配置
 * @constructor
 */
const FileConfig: React.FC<Props> = (props) => {
  const { data } = props;

  const fileConfig = data?.fileConfig;
  if (!fileConfig) {
    return <></>;
  }

  const items: DescriptionsProps['items'] = [
    {
      key: 'inputRootPath',
      label: '输入根路径',
      children: <p>{fileConfig.inputRootPath}</p>,
    },
    {
      key: 'outputRootPath',
      label: '输出根路径',
      children: <p>{fileConfig.outputRootPath}</p>,
    },
    {
      key: 'sourceRootPath',
      label: '项目根路径',
      children: <p>{fileConfig.sourceRootPath}</p>,
    },
    {
      key: 'type',
      label: '文件类别',
      children: <p>{fileConfig.type}</p>,
    },
  ];

  const fileListView = (files?: API.FileInfoDTO[]) => {
    if (!files) {
      return <></>;
    }

    return (
      <>
        {files.map((file, index) => {
          // 是分组
          if (file.groupKey) {
            const groupFileItems: DescriptionsProps['items'] = [
              {
                key: 'groupKey',
                label: '分组key',
                children: <div>{file.groupKey}</div>,
              },
              {
                key: 'groupName',
                label: '分组名',
                children: <div>{file.groupName}</div>,
              },
              {
                key: 'condition',
                label: '条件',
                children: <div>{file.condition}</div>,
              },
              {
                key: 'files',
                label: '组内文件',
                children: <div>{fileListView(file.files)}</div>,
              },
            ];

            return (
              <Descriptions key={index} column={1} title={file.groupName} items={groupFileItems} />
            );
          }

          const fileItems: DescriptionsProps['items'] = [
            {
              key: 'inputPath',
              label: '输入路径',
              children: <div>{file.inputPath}</div>,
            },
            {
              key: 'outputPath',
              label: '输出路径',
              children: <div>{file.outputPath}</div>,
            },
            {
              key: 'type',
              label: '文件类别',
              children: <div>{file.type}</div>,
            },
            {
              key: 'generateType',
              label: '文件生成类别',
              children: <div>{file.generateType}</div>,
            },
            {
              key: 'condition',
              label: '条件',
              children: <div>{file.condition}</div>,
            },
          ];

          return (
            <div key={index}>
              <Descriptions column={2} key={index} items={fileItems} />
              <Divider key={`divider_${index}`} />
            </div>
          );
        })}
      </>
    );
  };

  return (
    <div>
      <Descriptions
        title={
          <>
            <InfoCircleOutlined /> 基本信息
          </>
        }
        column={2}
        items={items}
      />
      <div style={{ marginBottom: 16 }} />
      <Descriptions
        title={
          <>
            <FileOutlined /> 文件列表
          </>
        }
      />
      {fileListView(fileConfig.files)}
    </div>
  );
};

export default FileConfig;
