package b172.challenging.myhome.dto;

import b172.challenging.myhome.domain.MyHome;

public record MyHomeResponseDto(
    Long id,
    String name,
    Long level
){

    public static MyHomeResponseDto from(MyHome myHome){
        return new MyHomeResponseDto(
                myHome.getId(),
                myHome.getName(),
                myHome.getLevel()
        );
    }
}
