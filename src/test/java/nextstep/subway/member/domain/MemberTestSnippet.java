package nextstep.subway.member.domain;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.subway.auth.dto.AuthToken;
import nextstep.subway.member.dto.MemberRequest;
import nextstep.subway.member.dto.MemberResponse;
import org.junit.jupiter.api.function.Executable;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.*;

public class MemberTestSnippet {
    public static ExtractableResponse<Response> 회원_생성을_요청(String email, String password, Age age) {
        MemberRequest memberRequest = new MemberRequest(email, password, age);

        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(memberRequest)
                .when().post("/members")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 회원_정보_조회_요청(ExtractableResponse<Response> response) {
        String uri = response.header("Location");

        return RestAssured
                .given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get(uri)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 토큰으로_회원_정보_조회_요청(AuthToken token) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(token.getAccessToken())
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/members/me")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 회원_정보_수정_요청(ExtractableResponse<Response> response, String email, String password, Age age) {
        String uri = response.header("Location");
        MemberRequest memberRequest = new MemberRequest(email, password, age);

        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(memberRequest)
                .when().put(uri)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 토큰으로_회원_정보_수정_요청(AuthToken token, String email, String password, Age age) {
        MemberRequest memberRequest = new MemberRequest(email, password, age);

        return RestAssured
                .given().log().all()
                .auth().oauth2(token.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(memberRequest)
                .when().put("/members/me")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 회원_삭제_요청(ExtractableResponse<Response> response) {
        String uri = response.header("Location");
        return RestAssured
                .given().log().all()
                .when().delete(uri)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 토큰으로_회원_삭제_요청(AuthToken token) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(token.getAccessToken())
                .when().delete("/members/me")
                .then().log().all()
                .extract();
    }

    public static void 회원_생성됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(CREATED.value());
    }

    public static void 회원_정보_조회됨(ExtractableResponse<Response> response, String email, Age age) {
        MemberResponse memberResponse = response.as(MemberResponse.class);
        assertThat(memberResponse.getId()).isNotNull();
        assertThat(memberResponse.getEmail()).isEqualTo(email);
        assertThat(memberResponse.getAge()).isEqualTo(age);
    }

    public static void 회원_정보_수정됨(ExtractableResponse<Response> updateResponse, ExtractableResponse<Response> createResponse, String newEmail, Age newAge) {
        assertThat(updateResponse.statusCode()).isEqualTo(OK.value());
        ExtractableResponse<Response> findResponse = 회원_정보_조회_요청(createResponse);
        MemberResponse memberResponse = findResponse.as(MemberResponse.class);

        assertThat(memberResponse.getEmail()).isEqualTo(newEmail);
        assertThat(memberResponse.getAge()).isEqualTo(newAge);
    }

    public static void 회원_삭제됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(NO_CONTENT.value());
    }

    public static void 회원_정보_조회_실패함(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(UNAUTHORIZED.value());
    }

    public static Executable 회원_생성_요청_및_성공_확인(String email, String correctPassword, Age age) {
        return () -> {
            ExtractableResponse<Response> response = 회원_생성을_요청(email, correctPassword, age);

            회원_생성됨(response);
        };
    }

    public static Executable 권한_없이_회원_정보_요청_및_실패_확인(AuthToken token) {
        return () -> {
            ExtractableResponse<Response> response = 토큰으로_회원_정보_조회_요청(token);

            회원_정보_조회_실패함(response);
        };
    }
}