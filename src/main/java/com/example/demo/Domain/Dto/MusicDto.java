package com.example.demo.Domain.Dto;

import com.example.demo.Domain.Entity.Music;
import com.example.demo.Domain.Entity.Notice;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class MusicDto {
	private int music_code;
	private String title;
	private String artist;
	private String music_path;
	private Long count;
	private String searchText;



	public MusicDto Of(Music music, String title, String artist, String music_path, Long count, int music_code) {
		MusicDto dto = new MusicDto();
		this.music_code = music.getMusic_code();
		this.title = music.getTitle();
		this.artist = music.getArtist();
		this.music_path = music.getMusic_path();
		this.count = music.getCount();
		this.searchText = searchText;
		return dto;
	}

}