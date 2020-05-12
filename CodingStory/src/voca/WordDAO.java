package voca;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;


public class WordDAO {

	private JdbcTemplate jdbcTemplate;
	private RowMapper<Word> wordRowMapper = 
			new RowMapper<Word>() {
				@Override
				public Word mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					Word word = new Word();
					word.setWord(rs.getString("word"));
					word.setDescription(rs.getString("description"));
					return word;
				}
			};

	public WordDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public Map<ResultMessage, String> insert(Word word) {
		int result = 0;
		
		if (!isHaveWord(word.getWord())) {
			result = jdbcTemplate.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection con)
						throws SQLException {
					// 파라미터로 전달받은 Connection을 이용해서 PreparedStatement 생성
					PreparedStatement pstmt = con.prepareStatement(
							"insert into WORDS (word, description) " +
									"values (?, ?)");
					// 인덱스 파라미터 값 설정
					pstmt.setString(1, word.getWord());
					pstmt.setString(2, word.getDescription());
					// 생성한 PreparedStatement 객체 리턴
					return pstmt;
				}
			});
		}
		
		if (result == 1) {
			return getEnumMap(ResultMessage.SUCCESS);
		} else {
			return getEnumMap(ResultMessage.OVERLAP);
		}
	}

	public List<Word> selectAll() {
		List<Word> results = jdbcTemplate.query("select * from WORDS order by word",
				wordRowMapper);
		return results;
	}

	public List<Word> getPage(String pageNum, String onePageViewCount) {
		int printCount = Integer.parseInt(onePageViewCount);
		int start = (Integer.parseInt(pageNum) - 1) * printCount;

		List<Word> results = jdbcTemplate.query(
					"select * "
				  + "from WORDS "
				  + "order by word "
				  + "limit ?, ?",
				wordRowMapper, start, printCount);
		return results;
	}
	
	public List<Word> quizList(String totalQuizNumber) {
		List<Word> words = selectAll();
		Collections.shuffle(words);
		
		int quizNum = Integer.parseInt(totalQuizNumber);
		quizNum = words.size() < quizNum ? words.size() : quizNum;
		
		return words.subList(0, quizNum);
	}
	
	public Map<ResultMessage, String> rightWordReader(Word word) {
		int result = 0;
		
		Word dbWord = selectByWord(word.getWord());
		if (dbWord != null && dbWord.getDescription().equals(word.getDescription())) {
			result = 1;
		}
		
		if (result == 1) {
			return getEnumMap(ResultMessage.SUCCESS);
		} else {
			return getEnumMap(ResultMessage.MISSING_WORD);
		}
	}
	
	public Word selectByWord(String word) {
		List<Word> results = jdbcTemplate.query(
				"select * from WORDS where word = ?",
				wordRowMapper, word);
		return results.isEmpty() ? null : results.get(0);
	}
	
	public boolean isHaveWord(String word) {
		return selectByWord(word) != null ? true : false;
	}
	
	public Map<ResultMessage, String> update(Word word) {
		int result = 0;
		
		if (isHaveWord(word.getWord())) {
			result = jdbcTemplate.update(
					"update WORDS set description = ? where word = ?",
					word.getDescription(), word.getWord());
		}
		
		if (result == 1) {
			return getEnumMap(ResultMessage.SUCCESS);
		} else {
			return getEnumMap(ResultMessage.FAIL);
		}
		
	}
	
	private Map<ResultMessage, String> getEnumMap(ResultMessage resultMessage) {
		Map<ResultMessage, String> map = new HashMap<>();
		map.put(resultMessage, resultMessage.getMessage());
		return map;
	}
	
	public int maxPage(String onePageViewCount) {
		Integer count = jdbcTemplate.queryForObject(
				"select count(*) from WORDS", Integer.class);
		return (count - 1) / Integer.parseInt(onePageViewCount) + 1;
	}
		
}
