package kr.co.kmarket.security;

import kr.co.kmarket.entity.SellerEntity;
import kr.co.kmarket.entity.UserEntity;
import kr.co.kmarket.repository.SellerRepo;
import kr.co.kmarket.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SecurityUserService implements UserDetailsService{

	@Autowired
	private UserRepo repo;

	@Autowired
	private SellerRepo srepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 스프링 시큐리티 인증 동작방식은 아이디/패스워드를 한 번에 조회하는 방식이 아닌
		// 아이디만 이용해서 사용자 정보를 로딩하고 나중에 패스워드를 검증하는 방식

		log.info("SecurityUserService...0 : " + username);

		UserDetails myUser = null;
		boolean isPresentUser = repo.findById(username).isPresent();

		log.info("SecurityUserService...1 : " + isPresentUser);

		if(isPresentUser){
			UserEntity user = repo.findById(username).get();
			log.info("here1 : "+user.getUid());

			if(user == null){
				throw new UsernameNotFoundException(username);
			}

			myUser = MyUserDetails.builder()
					.user(user)
					.build();

		}else {
			log.info("SecurityUserService...2");

			boolean isPresentSeller = srepo.findById(username).isPresent();

			log.info("SecurityUserService...3 : " + isPresentSeller);

			if(isPresentSeller){

				SellerEntity seller = srepo.findById(username).get();

				log.info("here2 : "+seller.getUid());

				if(seller == null){
					throw new UsernameNotFoundException(username);
				}

				log.info("here3");

				myUser = MySellerDetails.builder()
						.user(seller)
						.build();

				log.info("here4");
			}
		}

		log.info("here5");
		return myUser;

	}

}
