package com.d2.pcu.utils;

import com.d2.pcu.data.model.map.temple.Temple;
import com.d2.pcu.data.model.map.temple.TempleGeo;
import com.d2.pcu.data.model.map.temple.diocese.Diocese;
import com.d2.pcu.data.model.map.temple.diocese.DioceseType;
import com.d2.pcu.data.model.map.temple.workers.Bishop;
import com.d2.pcu.data.model.map.temple.workers.Presiding;

import java.util.Arrays;
import java.util.List;

public class MockCreator {

    public static List<Temple> getTemplesMock() {
        //
        Temple sofiChurche = new Temple();
        sofiChurche.setName("Собор Софія Київська");
        sofiChurche.setHistory("Православный собор 1037 года с монастырскими зданиями, позолоченным куполом, некрополем и музеем.");
        sofiChurche.setId(1);
        sofiChurche.setPhone("0442782620");
        sofiChurche.setImageUrl("https://lh6.googleusercontent.com/proxy/dtceMUzchw01PXcFgEZyO6mIjLDwnIFYWFw3-QkcjVnw1oh1N7N76nrIO5hG0HaotIWOoEcebBqFUZ4cX3IqQTANPJ_shsC6gASkSJDK8F3h9IOgvdumFAKuTGEJ3AiGtAqCJDd9lCCu_asOmjdp9L-xrQHWW2k=w408-h272-k-no");
        sofiChurche.setSite("n.sophiakievska.org");

        TempleGeo templeGeo = new TempleGeo();
        templeGeo.setDistrict("Shevchenkivskii");
        templeGeo.setId(1);
        templeGeo.setIndex("01001");
        templeGeo.setLocality("Kyiv");
        templeGeo.setRegion("Kyiv");
        templeGeo.setStreet("Volodymyrska 24");
        templeGeo.setLt(50.4427473f);
        templeGeo.setLg(30.5167531f);

        sofiChurche.setTempleGeo(templeGeo);
        //

        //
        Temple lavra = new Temple();
        lavra.setHistory("Первая половина XI века – священник села Берестова Иларион выкопал на близлежащем холме пещеру для молитвенных подвигов.\n" +
                "1051 – Избрание киевским князем Ярославом Мудрым священника Илариона Киевским Митрополитом. Приход в Иларионову  пещеру преподобного Антония и основание им Киево-Печерского монастыря и Дальних пещер.\n" +
                "1062 – Избрание Варлаама первым Печерским игуменом. Создание преподобным Антонием Ближних пещер, переселение монахов из пещер в наземные кельи.\n" +
                "1063 – Избрание Феодосия Печерским игуменом.\n" +
                "1073 – Основание главного монастырского храма – Успенского собора. Почил о Господе Антоний Печерский. Завершение Никоном летописного свода.\n" +
                "1074 – Почил о Господе  Феодосий Печерский.  Избрание игуменом Стефана.\n" +
                "1078 – Избрание Никона Печерским игуменом.\n" +
                "1089 – Освящение Успенского собора.\n" +
                "1091 – Перенесение мощей Феодосия из Дальних пещер в Успенский собор.\n" +
                "1096 – Нападение на монастырь половцев под предводительством хана Боняка.\n" +
                "1106 – Строительство Троицкой надвратной церкви Николой Святошей, князем Черниговским, принявшим святой иноческий образ в Печерском монастыре.");

        lavra.setName("Святая Успенская Киево-Печерская Лавра");
        lavra.setSite("https://lavra.ua/");
        lavra.setImageUrl("https://lh5.googleusercontent.com/p/AF1QipOBgfKvQ5Amo0g7CtPel2-BB6IYwS-ZienAjhby=w408-h544-k-no");
        lavra.setPhone("044 255 1105");
        lavra.setId(2);

        TempleGeo geo = new TempleGeo();
        geo.setLt(50.43897f);
        geo.setLg(30.5359428f);
        geo.setStreet("Лаврська, 15");
        geo.setIndex("01015");
        geo.setRegion("Kyiv");
        geo.setDistrict("Pechersk");
        geo.setLocality("Ktiv");
        geo.setId(2);

        lavra.setTempleGeo(geo);

        Bishop bishop = new Bishop();
        bishop.setId(1);
        bishop.setName("Блаженнейший Митрополит Киевский и всея Украины Онуфрий, Предстоятель Украинской Православной Церкви");
        lavra.setBishop(bishop);


        Presiding presiding = new Presiding();
        presiding.setId(1);
        presiding.setName("Митрополит Вышгородский и Чернобыльский Павел");
        lavra.setPresiding(presiding);

        Diocese diocese = new Diocese();
        diocese.setId(1);
        diocese.setName("Monastir");

        lavra.setDiocese(diocese);

        DioceseType dioceseType = new DioceseType();
        diocese.setId(1);
        dioceseType.setType("Monastir");
        lavra.setDioceseType(dioceseType);
        //

        Temple temple = new Temple();
        TempleGeo templeGeo1 = new TempleGeo();
        templeGeo1.setLg(51.43897f);
        templeGeo1.setLt(29.5359428f);
        temple.setTempleGeo(templeGeo1);

        Temple temple1 = new Temple();
        TempleGeo templeGeo2 = new TempleGeo();
        templeGeo2.setLg(52f);
        templeGeo2.setLt(28f);
        temple1.setTempleGeo(templeGeo2);

        Temple temple2 = new Temple();
        TempleGeo templeGeo3 = new TempleGeo();
        templeGeo3.setLg(53f);
        templeGeo3.setLt(27f);
        temple2.setTempleGeo(templeGeo3);

        Temple temple3 = new Temple();
        TempleGeo templeGeo4 = new TempleGeo();
        templeGeo4.setLg(54f);
        templeGeo4.setLt(26f);
        temple3.setTempleGeo(templeGeo4);

        Temple temple4 = new Temple();
        TempleGeo templeGeo5 = new TempleGeo();
        templeGeo5.setLg(55f);
        templeGeo5.setLt(27f);
        temple.setTempleGeo(templeGeo5);

        return Arrays.asList(sofiChurche, lavra, temple1, temple2, temple3, temple4);
    }
}
