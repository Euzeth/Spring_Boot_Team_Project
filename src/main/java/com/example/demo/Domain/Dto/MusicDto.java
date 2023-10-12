package com.example.demo.Domain.Dto;

import com.example.demo.Domain.Entity.Music;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class MusicDto {
	private Long music_code;
	private String title;
	private String artist;
	private String music_path;
	private Long mlike;
	private Long count;
	private String searchText; //찾을 키워드
	private String type; //title, artist


	public MusicDto(){
		searchText = searchText;
		type = type;
	}



	public static MusicDto Of(Music music) {
		MusicDto dto = new MusicDto();
		dto.music_code = music.getMusic_code();
		dto.title = music.getTitle();
		dto.artist = music.getArtist();
		dto.music_path = music.getMusic_path();
		dto.mlike = music.getMlike();
		dto.count = music.getCount();
		return dto;
	}

}