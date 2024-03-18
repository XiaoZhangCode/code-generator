// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** sendVerificationCode POST /api/reCaptcha/sendEmailCode */
export async function sendVerificationCodeUsingPost(
  body: API.EmailVo,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseBoolean_>('/api/reCaptcha/sendEmailCode', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}
