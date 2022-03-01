package com.springboot.first.app.service.impl;

import org.springframework.stereotype.Service;

import com.springboot.first.app.model.Image;
import com.springboot.first.app.repository.ImageRepository;
import com.springboot.first.app.service.ImageService;

@Service
public class ImageServiceImpl implements ImageService{
	
	private ImageRepository imageRepository;
	
	
	public ImageServiceImpl(ImageRepository imageRepository) {
		super();
		this.imageRepository = imageRepository;
	}


	@Override
	public Image saImage(Image image) {
		// TODO Auto-generated method stub
		return imageRepository.save(image);
	}

	
}
