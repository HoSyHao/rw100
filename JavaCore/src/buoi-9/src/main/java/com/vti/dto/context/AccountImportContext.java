package com.vti.dto.context;

import java.util.Set;

/**
 * Lớp ngữ cảnh chứa các thông tin tra cứu từ Database phục vụ cho việc đối chiếu
 * validate dữ liệu nhập của danh sách tài khoản (Account) trong lần import CSV.
 */
public class AccountImportContext {
    private final Set<String> existingEmails;
    private final Set<String> existingUsernames;
    private final Set<Integer> existingDepIds;
    private final Set<Integer> existingPosIds;

    public AccountImportContext(
        Set<String> existingEmails, 
        Set<String> existingUsernames, 
        Set<Integer> existingDepIds, 
        Set<Integer> existingPosIds
    ) {
        this.existingEmails = existingEmails;
        this.existingUsernames = existingUsernames;
        this.existingDepIds = existingDepIds;
        this.existingPosIds = existingPosIds;
    }

    public Set<String> getExistingEmails() {
        return existingEmails;
    }

    public Set<String> getExistingUsernames() {
        return existingUsernames;
    }

    public Set<Integer> getExistingDepIds() {
        return existingDepIds;
    }

    public Set<Integer> getExistingPosIds() {
        return existingPosIds;
    }
}
