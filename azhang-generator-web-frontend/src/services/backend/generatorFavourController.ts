// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** doGeneratorFavour POST /api/generator_favour/ */
export async function doGeneratorFavourUsingPost(
  body: API.GeneratorFavourAddRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseInt_>('/api/generator_favour/', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** listFavourGeneratorByPage POST /api/generator_favour/list/page */
export async function listFavourGeneratorByPageUsingPost(
  body: API.GeneratorFavourQueryRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponsePageGeneratorVO_>('/api/generator_favour/list/page', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** listMyFavourGeneratorByPage POST /api/generator_favour/my/list/page */
export async function listMyFavourGeneratorByPageUsingPost(
  body: API.GeneratorQueryRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponsePageGeneratorVO_>('/api/generator_favour/my/list/page', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}
