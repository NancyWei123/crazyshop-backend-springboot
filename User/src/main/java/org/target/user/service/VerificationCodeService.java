package org.target.user.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class VerificationCodeService {

    private final Map<String, CodeInfo> codeMap = new ConcurrentHashMap<>();

    public void saveCode(String email, String code) {
        codeMap.put(email, new CodeInfo(code, LocalDateTime.now().plusMinutes(5)));
    }

    public boolean verifyCode(String email, String code) {
        CodeInfo codeInfo = codeMap.get(email);

        if (codeInfo == null) {
            return false;
        }

        if (LocalDateTime.now().isAfter(codeInfo.expireTime)) {
            codeMap.remove(email);
            return false;
        }

        boolean matched = codeInfo.code.equals(code);

        if (matched) {
            codeMap.remove(email);
        }

        return matched;
    }

    private static class CodeInfo {
        private final String code;
        private final LocalDateTime expireTime;

        public CodeInfo(String code, LocalDateTime expireTime) {
            this.code = code;
            this.expireTime = expireTime;
        }
    }
}