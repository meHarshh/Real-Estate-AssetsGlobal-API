package org.assetsglobal.serviceimpl;

import java.io.IOException;

import org.assetsglobal.entity.Multimedia;
import org.assetsglobal.repository.MultimediaRepository;
import org.assetsglobal.service.MultimediaService;
import org.assetsglobal.utility.SimpleResponseStructure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MultimediaServiceImpl implements MultimediaService {

	@Autowired
	private MultimediaRepository multimediaRepository;

	@Override
	public ResponseEntity<byte[]> getMedia(String mediaId) {
		return multimediaRepository.findById(mediaId)
				.map(media -> ResponseEntity.ok().contentType(MediaType.valueOf(media.getContentType()))
						.contentLength(media.getMediaBytes().length).body(media.getMediaBytes()))
				.orElseThrow();
	}

	@Override
	public ResponseEntity<SimpleResponseStructure> saveImage(MultipartFile file, int developerID) throws IOException {
		Multimedia media = Multimedia.builder().developerId(developerID).mediaBytes(file.getBytes())
				.contentType(file.getContentType()).build();

		multimediaRepository.save(media);

		return ResponseEntity.ok(SimpleResponseStructure.builder().statusCode(HttpStatus.OK.value())
				.message("media saved successfully").build());
	}

	@Override
	public ResponseEntity<byte[]> getMedias(int developerId) {
		return multimediaRepository.findByDeveloperId(developerId)
				.map(media -> ResponseEntity.ok().contentType(MediaType.valueOf(media.getContentType()))
						.contentLength(media.getMediaBytes().length).body(media.getMediaBytes()))
				.orElseThrow();
	}
}