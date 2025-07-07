package shop.nhnteam04.front.account.user.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaycoUserInfoResponse {

    private Header header;
    private UserData data;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Header {
        private boolean isSuccessful;
        private int resultCode;
        private String resultMessage;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserData {
        private Member member;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Member {
        private String idNo;
        private String email;
        private String maskedEmail;
        private String name;
        private String genderCode;
        private String birthdayMMdd;
        private String ageGroup;
    }
}
