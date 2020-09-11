package model.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Insert;

public interface ChatMapper {

	@Insert("insert into chat (id,regdate,content) "
			+ "value(#{id},now(),#{content})")
	void insert(Map<String, Object> map);

}
