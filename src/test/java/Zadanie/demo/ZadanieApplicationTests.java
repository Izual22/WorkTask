package Zadanie.demo;

import org.junit.jupiter.api.Test;
import org.kohsuke.github.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class ZadanieApplicationTests {

	@Autowired
	private MockMvc mockup;
	@MockitoBean
	private GitHub gitHub;
	@Test
	void happyPath() throws Exception{
		String login="testuser";
		GHUser user=mock(GHUser.class);
		when(gitHub.getUser(login)).thenReturn(user);
		GHRepository rep=mock(GHRepository.class);
		when(rep.getName()).thenReturn("Present");
		GHBranch branch=mock(GHBranch.class);
		when(branch.getName()).thenReturn("Branch Name");
		when(branch.getSHA1()).thenReturn("Sha");
		Map<String,GHBranch> branches=new HashMap<>();
		branches.put("main",branch);
		when(rep.getBranches()).thenReturn(branches);
		List<GHRepository> repoList=mock(List.class);
		when(user.listRepositories().toList()).thenReturn(repoList);
		when(repoList).thenReturn(List.of(rep));
		when(branch.getOwner()).thenReturn(rep);
		mockup.perform(get("/" + login))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name").value("Present"))
				.andExpect(jsonPath("$[0].login").value("testuser"))
				.andExpect(jsonPath("$[0].branches[0].name").value("Branch Name"))
				.andExpect(jsonPath("$[0].branches[0].commit").value("Sha"));

	}
}
