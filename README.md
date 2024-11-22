Hilt없이 사용하는 Retrofit 

Query는 https://jsonplaceholder.typicode.com/albums?userId=3 이런식이고
Path는 https://jsonplaceholder.typicode.com/albums/3 이런식이다

implementation("com.squareup.okhttp3:logging-interceptor:4.12.0") 해당 종속성을 추가하는데
그 이유는 logging interceptor 기능을 사용하려고 그런다
logging interceptor 기능은 앱에서 발생한 네트워크 작업 로그를 보여준다
