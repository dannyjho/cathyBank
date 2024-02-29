# 國泰世華JAVA engineer線上作業

面試用

## 項目架構
- SpringBoot 3
- Java 17
- Spring Data JPA
- Maven

## 功能簡述
1. 呼叫 coindesk API，解析其下行內容與資料轉換，並實作新的 API。
coindesk API：https://api.coindesk.com/v1/bpi/currentprice.json
2. 建立一張幣別與其對應中文名稱的資料表（需附建立 SQL 語法），並提供
查詢 / 新增 / 修改 / 刪除 功能 API。
3. 查詢幣別請依照幣別代碼排序。

## 加分項目實作
1. 能夠運行在 Docker

## 使用方法

1. clone 項目到本地
```
git clone https://github.com/yourusername/cathybank.git
```
2. 進到項目目錄
```
cd cathybank
```
3. build docker image
```
docker build -t cathybank .
```
4. run 
```
docker run -d -p 9527:9090 --name cathybank cathybank
```
5. 訪問啓動項目
```
http://localhost:9527
```

## h2 database url
```
http://localhost:9527/h2-console/
```
