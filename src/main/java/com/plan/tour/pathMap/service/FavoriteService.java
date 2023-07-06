package com.plan.tour.pathMap.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.plan.tour.pathMap.domain.favorite.FavoriteDto;
import com.plan.tour.pathMap.mapper.FavoriteMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteMapper favoriteMapper;

    @Transactional
    public void toggleFavorite(FavoriteDto favoriteDto){

        if (!isFavorite(favoriteDto)){
            favoriteMapper.insertFavorite(favoriteDto);
        } else {
            favoriteMapper.deleteFavorite(favoriteDto);
        }
    }

    @Transactional
    public boolean insertFavorite(FavoriteDto favoriteDto){

        if (!isFavorite(favoriteDto)) {
            favoriteMapper.insertFavorite(favoriteDto);
            return true;
        }

        return false;
    }

    @Transactional
    public void deleteFavorite(FavoriteDto favoriteDto){
        favoriteMapper.deleteFavorite(favoriteDto);
    }

    @Transactional
    public boolean isFavorite(FavoriteDto favoriteDto) {
        int count = favoriteMapper.checkFavorite(favoriteDto);

        // 추천을 하지 않았다면
        if (count == 0){
            return false;
        }

        return true;
    }
}
