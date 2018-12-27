# 1 User

## 1.1 Login

### 1.1.1 HTTP URL

```http
/Login?id=email_address&password=password
```

### 1.1.2 Return Value

#### status code 1

登陆成功，返回token，失效时间一年

```json
{"Status":{"Result":1},"Token":{"Token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJJZCI6InRteS4xMjI1QGZveG1haWwuY29tIiwiZXhwIjoxNTc2ODk3NTg4fQ.Lcy83lCJ5sJnVyQIK5ESoz3RhpUJ6rKWsscHtyTE1k0"}}
```

#### status code 2

密码错误，登陆失败

``` json
{"Status":{"Result":2},"Token":{}}
```

#### status code 3

生成token异常，登陆失败

``` json
{"Status":{"Result":3},"Token":{}}
```

#### status code 4

登陆日志插入异常，登陆失败，删除已生成token

``` json
{"Status":{"Result":4},"Token":{}}
```

## 1.2 Register

### 1.2.1 HTTP URL

``` http
/Register?id=email_address&password=password
```

### 1.2.2 Return Value

#### status code 1

申请注册成功，发送验证码

``` json
{"Status":{"Result":1},"Strength":3}
```

#### status code 2

验证码发送失败，注册失败

``` json
{"Status":{"Result":2},"Strength":3}
```

#### status code 3

id已存在，注册失败

``` json
{"Status":{"Result":3},"Strength":3}
```

#### status code 5

密码强度太弱，注册失败

``` json
{"Status":{"Result":5},"Strength":2}
```

## 1.3 Search User

模糊匹配用户id

### 1.3.1 HTTP URL

``` http
/SearchAllUsers?token=token&id=matching_part
```

### 1.3.2 Return Value

#### Status code 1

搜索成功，返回匹配的用户id列表

``` json
{"Status":{"Result":1},"Users":{"User Id":"tmy.1225@foxmail.com"}}
```

#### status code 2

查询异常

``` json
{"Status":{"Result":2},"Users":{}}
```

#### status code 3

token已失效

``` json
{"Status":{"Result":3},"Users":{}}
```

#### status code 4

token不存在

``` json
{"Status":{"Result":4},"Users":{}}
```

# 2 Log

## 2.1 Get All Logs

### 2.1.1 HTTP URL

``` http
/GetAllLogs?token=token
```

### 2.1.2 Return Value

#### status code 1

查询成功，返回全部登陆日志

``` json
{"Status":{"Result":1},"Logs":{"2018-12-21 11:06:28":"Last Login: 2018-12-21 11:06:28","2018-12-21 14:31:23":"Last Login: 2018-12-21 14:31:23"}}
```

#### status code 2

查询失败

``` json
{"Status":{"Result":2},"Logs":{}}
```

#### status code 3

token已失效

``` json
{"Status":{"Result":3},"Logs":{}}
```

#### status code 4

token不存在

``` json
{"Status":{"Result":4},"Log":{}}
```

## 2.2 Get the Latest Log

### 2.2.1 HTTP URL

``` http
/GetLatestLog?token=token
```

### 2.2.2 Return Value

#### status code 1

查询成功，返回最近一次的登陆日志

``` json
{"Status":{"Result":1},"Log":{"2018-12-21 14:31:23":"Last Login: 2018-12-21 14:31:23"}}
```

#### status code 2

查询失败

``` json
{"Status":{"Result":2},"Log":{}}
```

#### status code 3

token已失效

``` json
{"Status":{"Result":3},"Log":{}}
```

#### status code 4

token不存在

``` json
{"Status":{"Result":4},"Log":{}}
```


# 3 Identity

## 3.1 Create Identity

### 3.1.1 HTTP URL

``` http
/CreateIdentity?name=identity_name&token=token
```

### 3.1.2 Return Value

#### status code 1

创建成功

``` json
{"Status":{"Result":1}}
```

#### status code 2

创建异常

``` json
{"Status":{"Result":2}}
```

#### status code 3

token已失效

``` json
{"Status":{"Result":3}}
```

#### status code 4

token不存在

``` json
{"Status":{"Result":4}}
```

## 3.2 Search Identity

### 3.2.1 HTTP URL

``` http
/SearchIdentityByUser?token=token
```

### 3.2.2 Return Value

#### status code 1

查询成功

``` json
{"Status":{"Result":1},"identities":{"name":"tmy"}}
```

#### status code 2

查询异常

``` json
{"Status":{"Result":2},"identities":{}}
```

#### status code 3

token已失效

``` json
{"Status":{"Result":3},"identities":{}}
```

#### status code 4

token不存在

``` json
{"Status":{"Result":4},"identities":{}}
```

# 4 Verify Code

注册之后向邮箱中发送四位字母数字混合验证码，验证邮箱，失效时间十分钟

## 4.1 Verify

### 4.1.1 HTTP URL

``` http
/VerifyCode?address=id&code=code
```

![1545372420829](.\1545372420829.png)

### 4.1.2 Return Value

#### status code 1

验证成功，完成用户注册

``` json
{"Status":{"Result":1}}
```

#### status code 2

验证码错误或已失效，用户注册失败

``` json
{"Status":{"Result":2}}
```

#### status code 3

验证成功，创建用户异常，用户注册失败

``` json
{"Status":{"Result":3}}
```



