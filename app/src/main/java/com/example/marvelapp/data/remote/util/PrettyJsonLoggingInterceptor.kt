import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor

class PrettyLoggingInterceptor : Interceptor {

    private val loggingInterceptor = HttpLoggingInterceptor { message ->
        // JSON 포맷팅
        val prettyJson = try {
            val gson = GsonBuilder().setPrettyPrinting().create()
            val jsonElement = gson.fromJson(message, Any::class.java)
            gson.toJson(jsonElement)
        } catch (e: Exception) {
            message // JSON 파싱 실패 시 원본 반환
        }

        // 예쁘게 포맷된 JSON을 로그로 출력
        println(prettyJson)
    }.apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        return loggingInterceptor.intercept(chain)
    }
}
