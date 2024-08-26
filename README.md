## 실무 모니터링 환경 팁

### 모니터링 3단계

- 대시보드
- 애플리케이션 추적 - 핀포인트
- 로그

### 모니터링 대상

- 시스템 메트릭(CPU, 메모리)
- 애플리케이션 메트릭(톰캣 쓰레드 풀, DB 커넥션 풀, 애플리케이션 호출 수)
- 비즈니스 메트릭(주문 수, 취소 수)

### 애플리케이션 추적

- 주로 각각의 HTTP 요청을 추적, 일부는 마이크로서비스 환경에서 분산 추적

### 로그

- 가장 자세한 추적, 원하는대로 커스텀 가능
- 같은 HTTP 요청을 묶어서 확인할 수 있는 방법이 중요, MDC 적용

#### 파일로 직접 로그를 남기는 경우

- 일반 로그와 에러 로그는 파일로 구분해서 남기자
- 에러 로그만 확인해서 문제를 바로 정리할 수 있음

#### 클라우드에 로그를 저장하는 경우

- 검색이 잘 되도록 구분

### 알람

- 모니터링 툴에서 일정 이상 수치가 넘어가면 슬랙, 문자 등을 연동

#### 알람은 2가지 종류로 꼭 구분해서 관리

- `경고, 심각`
- 경고는 하루 1번 정도 사람이 직접 확인해도 되는 수준(사람이 들어가서 확인)
- 심각은 즉시 확인 해야 함, 슬랙 알림(앱을 통해 알림을 받도록), 문자, 전화
- 예시)
    - 디스크 사용량 70% -> 경고
    - 디스크 사용량 80% -> 심각
    - CPU 사용량 40% -> 경고
    - CPU 사용량 50% -> 심각

## prometheus.yml

```yaml
# my global config
global:
  scrape_interval: 15s # Set the scrape interval to every 15 seconds. Default is every 1 minute.
  evaluation_interval: 15s # Evaluate rules every 15 seconds. The default is every 1 minute.
  # scrape_timeout is set to the global default (10s).

# Alertmanager configuration
alerting:
  alertmanagers:
    - static_configs:
        - targets:
          # - alertmanager:9093

# Load rules once and periodically evaluate them according to the global 'evaluation_interval'.
rule_files:
# - "first_rules.yml"
# - "second_rules.yml"

# A scrape configuration containing exactly one endpoint to scrape:
# Here it's Prometheus itself.
scrape_configs:
  # The job name is added as a label `job=<job_name>` to any timeseries scraped from this config.
  - job_name: "prometheus"

    # metrics_path defaults to '/metrics'
    # scheme defaults to 'http'.

    static_configs:
      - targets: [ "localhost:9090" ]

  - job_name: "spring-actuator"
    metrics_path: "/actuator/prometheus"
    scrape_interval: 1s
    static_configs:
      - targets: [ "localhost:8080" ]

```