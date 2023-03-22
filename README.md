# API 명세서

## 블로그 검색

### 기본 정보
GET /blog/search<br>
HOST: localhost:8080

### Request
Name	Type	Description	Required

| Name  | Type    | Description                              | Required |
|-------|---------|------------------------------------------|----------|
| query | String  | 검색 질의어                                   | O        |
| sort  | String  | 정렬 방식: accuracy(정확도순, 기본값), recency(최신순) | X        |
| page  | Integer | 결과 페이지 번호, 최소 1, 최대 50, 기본값 1            | X        |
| size  | Integer | 한 페이지에 보여질 문서 수, 최소 1, 최대 50, 기본 값 10    | X        |

### Response
meta

| Name           | Type    | Description |
|----------------|---------|-------------|
| total_count    | Integer | 검색된 문서 수    |
| pageable_count | Integer | 노출 가능한 문서 수 |
| is_end         | Boolean | 마지막 페이지 여부  |

documents

| Name           | Type    | Description  |
|----------------|---------|--------------|
| title    | String | 블로그 글 제목     |
| contents | String | 블로그 글 요약     |
| url         | String | 블로그 글 URL    |
| blogname         | String | 블로그 이름       |
| thumbnail         | String | 미리보기 이미지 URL |
| datetime         | Datetime | 블로그 글 작성시간    |

---

## 인기검색어 조회

### 기본 정보
GET /blog/top<br>
HOST: localhost:8080

### Request
Name	Type	Description	Required

| Name  | Type    | Description                              | Required |
|-------|---------|------------------------------------------|--------|
|  |   |                         |        |

### Response

| Name    | Type    | Description |
|---------|---------|-------------|
| id      | Integer | 순번          |
| keyword | String  | 검색어         |
| count   | Integer | 검색된 횟수      |
| lastUpdateTime   | String  | 최종 업데이트 일자  |

---

## 빌드 결과물 링크
!\[https://github.com/garamm/search-app/blob/main/search-app.jar](https://github.com/garamm/search-app/blob/main/search-app.jar)
