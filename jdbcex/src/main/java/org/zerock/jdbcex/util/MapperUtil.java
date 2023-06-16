package org.zerock.jdbcex.util;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;

// ModelMapper의 설정을 변경하고 쉽게 사용할 수 있는 MapperUtil을 enum으로 사용
public enum MapperUtil {
    INSTANCE;

    private ModelMapper modelMapper;

    MapperUtil(){
        this.modelMapper = new ModelMapper();
        this.modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                // 프라이빗 필드에 접근해 매핑을 수행할 수 있도록 설정
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                // 엄격한 매칭 전략을 사용해 매핑 수행
                .setMatchingStrategy(MatchingStrategies.STRICT);
    }

    // MapperUtil.INSTANCE.get() 호출해 ModelMapper 사용 가능
    public ModelMapper get(){
        return modelMapper;
    }
}
