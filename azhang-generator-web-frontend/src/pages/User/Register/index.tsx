import Footer from '@/components/Footer';
import { sendVerificationCodeUsingPost } from '@/services/backend/reCaptchaController';
import { userRegisterUsingPost as userRegister } from '@/services/backend/userController';
import { LockOutlined, SafetyOutlined, UserOutlined } from '@ant-design/icons';
import { LoginForm, ProFormText } from '@ant-design/pro-components';
import { useEmotionCss } from '@ant-design/use-emotion-css';
import { Helmet, history } from '@umijs/max';
import { Button, message, Tabs } from 'antd';
import React, { useState } from 'react';
import { Link } from 'umi';
import Settings from '../../../../config/defaultSettings';

const Register: React.FC = () => {
  const [type, setType] = useState<string>('account');

  const [email, setEmail] = useState<string>('');

  const [isSending, setIsSending] = useState(false);
  const [countdown, setCountdown] = useState(0);

  const containerClassName = useEmotionCss(() => {
    return {
      display: 'flex',
      flexDirection: 'column',
      height: '100vh',
      overflow: 'auto',
      backgroundImage:
        "url('https://mdn.alipayobjects.com/yuyan_qk0oxh/afts/img/V-_oS6r-i7wAAAAAAAAAAAAAFl94AQBr')",
      backgroundSize: '100% 100%',
    };
  });

  const handleSubmit = async (values: API.UserRegisterRequest) => {
    try {
      // 注册
      await userRegister({
        ...values,
      });
      const defaultRegisterSuccessMessage = '注册成功！';
      message.success(defaultRegisterSuccessMessage);
      // 注册成功后跳转到登录页面
      history.push('/user/login');
      return;
    } catch (error: any) {
      const defaultRegisterFailureMessage = `注册失败，${error.message}`;
      message.error(defaultRegisterFailureMessage);
    }
  };

  // 倒计时函数
  const startCountdown = () => {
    const interval = setInterval(() => {
      setCountdown((prevCountdown) => {
        if (prevCountdown > 1) {
          return prevCountdown - 1;
        } else {
          setIsSending(false);
          clearInterval(interval);
          return 0;
        }
      });
    }, 1000);
    // 组件卸载时清除倒计时
    return () => clearInterval(interval);
  };

  const buttonText = isSending ? (countdown ? `${countdown}s 后重发` : '发送中...') : '获取验证码';

  const handleSendCode = async (e: any) => {
    e.preventDefault();
    setIsSending(true);
    try {
      await sendVerificationCodeUsingPost({ userEmail: email });
      setCountdown(10);
      startCountdown();
      message.success('发送成功');
    } catch (error: any) {
      setIsSending(false);
      message.error(error.message);
    }
  };

  return (
    <div className={containerClassName}>
      <Helmet>
        <title>
          {'注册'}- {Settings.title}
        </title>
      </Helmet>
      <div
        style={{
          flex: '1',
          padding: '32px 0',
        }}
      >
        <LoginForm<API.UserRegisterRequest>
          contentStyle={{
            minWidth: 280,
            maxWidth: '75vw',
          }}
          logo={<img alt="logo" style={{ height: '100%' }} src="/logo.png" />}
          title="CodeXpress"
          subTitle={'开发者的得力助手，让代码生成变得简单、快捷。'}
          initialValues={{
            autoRegister: true,
          }}
          submitter={{
            searchConfig: {
              submitText: '注册',
            },
          }}
          onFinish={async (values) => {
            await handleSubmit(values as API.UserRegisterRequest);
          }}
        >
          <Tabs
            activeKey={type}
            onChange={setType}
            centered
            items={[
              {
                key: 'account',
                label: '新用户注册',
              },
            ]}
          />
          {type === 'account' && (
            <>
              <ProFormText
                name="userAccount"
                fieldProps={{
                  size: 'large',
                  prefix: <UserOutlined />,
                }}
                placeholder={'请输入账号'}
                rules={[
                  {
                    required: true,
                    message: '账号是必填项！',
                  },
                ]}
              />
              <ProFormText
                name="userEmail"
                fieldProps={{
                  size: 'large',
                  prefix: <SafetyOutlined />,
                  onChange: (e) => {
                    setEmail(e.target.value);
                  },
                }}
                placeholder={'请输入邮箱'}
                rules={[
                  {
                    required: true,
                    message: '邮箱是必填项！',
                  },
                ]}
              />
              <div style={{ display: 'flex', justifyContent: 'space-between' }}>
                <ProFormText
                  name="code"
                  fieldProps={{
                    size: 'large',
                    prefix: <SafetyOutlined />,
                  }}
                  placeholder={'请输入验证码'}
                  rules={[
                    {
                      required: true,
                      message: '验证码是必填项！',
                    },
                  ]}
                />
                <Button
                  type="primary"
                  style={{ marginLeft: '10px', marginTop: '5px' }}
                  onClick={isSending ? () => {} : handleSendCode}
                >
                  {buttonText}
                </Button>
              </div>

              <ProFormText.Password
                name="userPassword"
                fieldProps={{
                  size: 'large',
                  prefix: <LockOutlined />,
                }}
                placeholder={'请输入密码'}
                rules={[
                  {
                    required: true,
                    message: '密码是必填项！',
                  },
                ]}
              />
              <ProFormText.Password
                name="checkPassword"
                fieldProps={{
                  size: 'large',
                  prefix: <LockOutlined />,
                }}
                placeholder={'请再次确认密码'}
                rules={[
                  {
                    required: true,
                    message: '确认密码是必填项！',
                  },
                ]}
              />
            </>
          )}

          <div
            style={{
              marginBottom: 24,
              textAlign: 'right',
            }}
          >
            <Link to="/user/login">老用户登录</Link>
          </div>
        </LoginForm>
      </div>
      <Footer />
    </div>
  );
};
export default Register;
