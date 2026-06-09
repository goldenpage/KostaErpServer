package com.oopsw.kostaerpserver.dto.addfoodmaterial;

import com.oopsw.kostaerpserver.vo.AddFoodMaterial;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddFoodMaterialRequest {
    private List<String> foodMaterialName;
    private List<String> foodCategory_Id;
    private List<String> foodMaterialCount;
    private List<String> foodMaterialCountAll;
    private List<String> foodMaterialPrice;
    private List<String> foodMaterialType;
    private List<String> vender;
    private List<String> incomeDate;
    private List<String> expirationDate;

    public List<AddFoodMaterial> VOList(String bId){
        List<AddFoodMaterial> list = new ArrayList<>();
        for(int i = 0; i < foodMaterialName.size(); i++){
            AddFoodMaterial vo = new AddFoodMaterial();
            vo.setFoodMaterialName(foodMaterialName.get(i));
            vo.setFoodCategory_Id(foodCategory_Id.get(i));
            vo.setFoodMaterialCount(Integer.parseInt(foodMaterialCount.get(i)));
            vo.setFoodMaterialCountAll(Integer.parseInt(foodMaterialCountAll.get(i)));
            vo.setFoodMaterialPrice(Integer.parseInt(foodMaterialPrice.get(i)));
            vo.setFoodMaterialType(foodMaterialType.get(i));
            vo.setVender(vender.get(i));
            vo.setIncomeDate(java.sql.Date.valueOf(incomeDate.get(i)));
            vo.setExpirationDate(java.sql.Date.valueOf(expirationDate.get(i)));
            vo.setBId(bId);
            list.add(vo);
        }
        return list;
    }
}
