# tkt-elasticsearch
elasticsearch용 한국어 형태소 분석기 플러그인 / korean analyzer for elasticsearch based on twitter-korean-text 

## Overview
[twitter-korean-text](https://github.com/twitter/twitter-korean-text)를 기반으로 elasticsearch용 tokenizer로 사용할 수 있도록 플러그인으로 제작했습니다.

twitter-korean-text는 4.1.4, elaisticsearch는 1.7.2 버전으로 테스트했습니다.

## Install
현재 버전(0.1.0)의 jar파일을 다운로드하여 설치한 후 재기동한다.
```bash
cd ${ES_HOME}/plugins
mkdir tkt-elasticsearch
cd tkt-elasticsearch/
wget https://github.com/socurites/tkt-elasticsearch/blob/master/dist/tkt-elasticsearch-0.1.0-jar-with-dependencies.jar?raw=true -O tkt-elasticsearch-0.1.0.jar
```

## Test
### 정규화:off / 스테밍: off
```json
PUT /test/
{
    "index" : {
        "analysis" : {
            "analyzer" : {
                "custom-analyzer" : {
                    "tokenizer" : "custom-tokenizer"
                }
            },
            "tokenizer": {
              "custom-tokenizer" : {
                "type": "tkt-korean-tokenizer",
                "enableNormalize": false,
                "enableStemmer": false
              }
            }
        }
    }
}

GET /test/_analyze?analyzer=custom-analyzer&text=한국어를 처리하는 예시입니닼ㅋㅋㅋㅋㅋ
```

결과:
```json
{
   "tokens": [
      {
         "token": "한국어",
         "start_offset": 0,
         "end_offset": 3,
         "type": "Noun",
         "position": 1
      },
      {
         "token": "를",
         "start_offset": 3,
         "end_offset": 4,
         "type": "Josa",
         "position": 2
      },
      {
         "token": "처리",
         "start_offset": 5,
         "end_offset": 7,
         "type": "Noun",
         "position": 3
      },
      {
         "token": "하는",
         "start_offset": 7,
         "end_offset": 9,
         "type": "Verb",
         "position": 4
      },
      {
         "token": "예시",
         "start_offset": 10,
         "end_offset": 12,
         "type": "Noun",
         "position": 5
      },
      {
         "token": "입니",
         "start_offset": 12,
         "end_offset": 14,
         "type": "Adjective",
         "position": 6
      },
      {
         "token": "닼",
         "start_offset": 14,
         "end_offset": 15,
         "type": "ProperNoun",
         "position": 7
      },
      {
         "token": "ㅋㅋㅋㅋㅋ",
         "start_offset": 15,
         "end_offset": 20,
         "type": "KoreanParticle",
         "position": 8
      }
   ]
}
```

### 정규화:on / 스테밍: on (default)
```json
DELETE /test/

PUT /test/
{
    "index" : {
        "analysis" : {
            "analyzer" : {
                "custom-analyzer" : {
                    "tokenizer" : "custom-tokenizer"
                }
            },
            "tokenizer": {
              "custom-tokenizer" : {
                "type": "tkt-korean-tokenizer"
              }
            }
        }
    }
}

GET /test/_analyze?analyzer=custom-analyzer&text=한국어를 처리하는 예시입니닼ㅋㅋㅋㅋㅋ
```
