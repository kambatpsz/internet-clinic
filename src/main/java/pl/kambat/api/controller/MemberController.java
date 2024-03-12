package pl.kambat.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.kambat.business.MemberService;
import pl.kambat.domain.InternetClinicMember;
import pl.kambat.api.dto.MemberDTO;
import pl.kambat.api.dto.mapper.MemberMapper;
import pl.kambat.domain.Member;
import pl.kambat.domain.exception.ProcessingDataException;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private static final String USER_ADD = "/user/add";
    private static final String USER_NEW = "/user/new";

    private final MemberMapper memberMapper;
    private final MemberService memberService;

    @GetMapping(USER_NEW)
    public String showNewUserPage(Model model) {
        model.addAttribute("memberDTO", MemberDTO.buildDefault());
//        model.addAttribute("memberDTO", new MemberDTO());
        return "member/new_member";
    }

    @PostMapping(USER_ADD)
    public String addMember(
            @Valid @ModelAttribute("memberDTO") MemberDTO memberDTO,
            BindingResult bindingResult,
            Model model
    ) {

        if (!bindingResult.hasErrors()) {
            Member member = memberMapper.map(memberDTO);
            InternetClinicMember internetClinicMember = memberService.addUser(member);

            if (internetClinicMember != null) {
                model.addAttribute("internetClinicMemberType", memberDTO.getUserType());
                model.addAttribute("internetClinicMemberName", memberDTO.getUserName());
                model.addAttribute("internetClinicMemberSurname", memberDTO.getUserSurname());
                return "member/add_member_done";
            }
        }
        log.info("Member not created, requirements have not been met");
        throw new ProcessingDataException("Member not created, requirements have not been met");
    }
}
