import { GithubOutlined } from '@ant-design/icons';
import { DefaultFooter } from '@ant-design/pro-components';
import '@umijs/max';
import React from 'react';

const Footer: React.FC = () => {
  const defaultMessage = 'codeZhang 联系邮箱 1687438992@qq.com ';
  const currentYear = new Date().getFullYear();
  return (
    <div>
      <DefaultFooter
        style={{
          background: 'none',
        }}
        //@ts-ignore
        copyright={
          <div style={{ display: 'inline-block', textAlign: 'center', color: '#333' }}>
            <a style={{ display: 'block', color: '#333' }} href="https://beian.miit.gov.cn">
              {currentYear} 湘ICP备2024049846号
            </a>
          </div>
        }
        links={[
          {
            key: 'github',
            title: (
              <>
                <GithubOutlined /> Github源码
              </>
            ),
            href: 'https://github.com/XiaoZhangCode/code-generator',
            blankTarget: true,
          },
        ]}
      />
      <div style={{ textAlign: 'center', color: '#333', margin: '0 0 20px' }}>{defaultMessage}</div>
    </div>
  );
};
export default Footer;
