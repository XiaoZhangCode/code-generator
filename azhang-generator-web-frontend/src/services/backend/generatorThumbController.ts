// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** doThumb POST /api/generator_thumb/ */
export async function doThumbUsingPost(
  body: API.GeneratorThumbAddRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseInt_>('/api/generator_thumb/', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}
