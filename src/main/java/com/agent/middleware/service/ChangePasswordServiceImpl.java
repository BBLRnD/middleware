package com.agent.middleware.service;
import com.agent.middleware.dto.ChangePasswordDto;
import com.agent.middleware.dto.ChangePasswordResponseDto;
import com.agent.middleware.repository.ChangePasswordRepository;
import org.springframework.stereotype.Service;

@Service
public class ChangePasswordServiceImpl implements ChangePasswordService {

    private final ChangePasswordRepository changePasswordRepository;

    public ChangePasswordServiceImpl(ChangePasswordRepository changePasswordRepository) {
        this.changePasswordRepository = changePasswordRepository;
    }

    @Override
    public boolean validateChangePasswordDto(ChangePasswordDto changePasswordDto) {
        return
                changePasswordDto.getLoginKeyOld() != null && !changePasswordDto.getLoginKeyOld().isEmpty() &&
                changePasswordDto.getLoginKeyNew() != null && !changePasswordDto.getLoginKeyNew().isEmpty() &&
                changePasswordDto.getLoginKeyRe() != null && !changePasswordDto.getLoginKeyRe().isEmpty() &&
                changePasswordDto.getLoginKeyNew().equals(changePasswordDto.getLoginKeyRe()) &&
                        !changePasswordDto.getLoginKeyOld().equals(changePasswordDto.getLoginKeyNew());
    }


    @Override
    public ChangePasswordResponseDto savePassword(ChangePasswordDto changePasswordDto) {
        return changePasswordRepository.saveChangePassword(changePasswordDto) ;
    }
}
