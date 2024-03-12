package pl.kambat.integration.controler;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.kambat.api.controller.MemberController;
import pl.kambat.api.controller.PatientPanelController;
import pl.kambat.api.dto.MemberDTO;
import pl.kambat.api.dto.mapper.MemberMapper;
import pl.kambat.business.MemberService;
import pl.kambat.domain.InternetClinicMember;
import pl.kambat.domain.Member;
import pl.kambat.util.DataFixtures;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MemberController.class)
@AutoConfigureMockMvc(addFilters = false)
class MemberControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberMapper memberMapper;
    @MockBean
    private MemberService memberService;

    @Test
    void showNewUserPage() throws Exception {
        mockMvc.perform(get("/user/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("member/new_member"))
                .andExpect(model().attributeExists("memberDTO"));
    }

    @Test
    void addMember() throws Exception {
        // given
        MemberDTO memberDTO = DataFixtures.someMemberDTO1();
        Member member = DataFixtures.someMember1();
        InternetClinicMember internetClinicMember = DataFixtures.someDoctor1();

        when(memberMapper.map(any(MemberDTO.class))).thenReturn(member);
        when(memberService.addUser(any(Member.class))).thenReturn(internetClinicMember);

        // when, then
        mockMvc.perform(post("/user/add")
                .flashAttr("memberDTO", memberDTO))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("memberDTO"))
                .andExpect(model().attributeExists("internetClinicMemberType"))
                .andExpect(model().attributeExists("internetClinicMemberName"))
                .andExpect(model().attributeExists("internetClinicMemberSurname"))
                .andExpect(view().name("member/add_member_done"))
                .andExpect(model().size(4));
    }

    @Test
    void addMember_ShouldThrowProcessingDataException() throws Exception {
        // given
        MemberDTO memberDTO = DataFixtures.someMemberDTO1();
        Member member = DataFixtures.someMember1();

        when(memberMapper.map(any(MemberDTO.class))).thenReturn(member);
        when(memberService.addUser(any(Member.class))).thenReturn(null);

        // when, then
        mockMvc.perform(post("/user/add")
                        .flashAttr("memberDTO", memberDTO))
                .andExpect(status().isInternalServerError())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("errorMessage",
                        "Processing exception occurred: [Member not created, requirements have not been met]"));
    }
}