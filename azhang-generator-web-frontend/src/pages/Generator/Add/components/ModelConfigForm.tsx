import { CloseOutlined } from '@ant-design/icons';
import { Button, Card, Form, FormListFieldData, Input, Select, Space } from 'antd';
import React from 'react';

interface Props {
  formRef: any;
  oldData: API.GeneratorEditRequest | undefined;
}

const ModelConfigForm: React.FC<Props> = (props) => {
  const { formRef, oldData } = props;
  const singleFieldFormView = (
    field: FormListFieldData,
    remove?: (index: number | number[]) => void,
  ) => (
    <Space key={field.key}>
      <Form.Item label="字段名称" name={[field.name, 'fieldName']}>
        <Input placeholder="请输入字段名称" />
      </Form.Item>
      <Form.Item label="字段类型" style={{ width: 150 }} name={[field.name, 'type']}>
        <Select style={{ width: '100%' }} placeholder="请选择字段类型">
          <Select.Option value="Integer">Integer</Select.Option>
          <Select.Option value="String">String</Select.Option>
          <Select.Option value="Boolean">Boolean</Select.Option>
        </Select>
      </Form.Item>
      <Form.Item label="描述信息" name={[field.name, 'description']}>
        <Input placeholder="请输入描述信息" />
      </Form.Item>
      <Form.Item label="默认值" name={[field.name, 'defaultValue']}>
        <Input placeholder="请输入字段的默认值" />
      </Form.Item>
      <Form.Item label="命令缩写" name={[field.name, 'abbr']}>
        <Input placeholder="请输入命令缩写" />
      </Form.Item>
      {remove && (
        <Button
          type="text"
          danger
          onClick={() => {
            remove(field.name);
          }}
        >
          删除
        </Button>
      )}
    </Space>
  );
  return (
    <Form.List name={['modelConfig', 'models']}>
      {(fields, { add, remove }) => (
        <div style={{ display: 'flex', rowGap: 16, flexDirection: 'column' }}>
          {fields.map((field) => {
            const modelConfig =
              formRef.current?.getFieldsValue().modelConfig ?? oldData?.modelConfig;
            const groupKey = modelConfig?.models?.[field.name]?.groupKey;
            return (
              <Card
                size="small"
                title={groupKey ? `分组模型参数` : '模型参数'}
                key={field.key}
                extra={
                  <CloseOutlined
                    onClick={() => {
                      remove(field.name);
                    }}
                  />
                }
              >
                {groupKey ? (
                  <>
                    <Space>
                      <Form.Item label="分组键名" name={[field.name, 'groupKey']}>
                        <Input placeholder="请输入分组键名" />
                      </Form.Item>
                      <Form.Item label="分组名称" name={[field.name, 'groupName']}>
                        <Input placeholder="请输入字段类型" />
                      </Form.Item>
                      <Form.Item label="分组类型" name={[field.name, 'type']}>
                        <Input placeholder="请输入字段类型" />
                      </Form.Item>
                      <Form.Item label="描述信息" name={[field.name, 'description']}>
                        <Input placeholder="请输入描述信息" />
                      </Form.Item>
                      <Form.Item label="分组条件" name={[field.name, 'condition']}>
                        <Input placeholder="请输入分组条件" />
                      </Form.Item>
                    </Space>
                  </>
                ) : (
                  singleFieldFormView(field)
                )}

                {groupKey && (
                  <Form.Item label="分组模型参数">
                    <Form.List name={[field.name, 'models']}>
                      {(subFields, subOpt) => (
                        <div style={{ display: 'flex', flexDirection: 'column', rowGap: 16 }}>
                          {subFields.map((subField) => {
                            return singleFieldFormView(subField, subOpt.remove);
                          })}
                          <Button type="dashed" onClick={() => subOpt.add()} block>
                            + 添加组内模型参数
                          </Button>
                        </div>
                      )}
                    </Form.List>
                  </Form.Item>
                )}
              </Card>
            );
          })}

          <Button type="dashed" onClick={() => add()} block>
            + 添加模型参数
          </Button>
          <Button
            type="dashed"
            onClick={() =>
              add({
                groupKey: '分组Key',
                groupName: '',
              })
            }
            block
          >
            + 添加分组
          </Button>
          <div style={{ marginBottom: 16 }}></div>
        </div>
      )}
    </Form.List>
  );
};

export default ModelConfigForm;
