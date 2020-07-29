package com.example.otushomework_01.data

import com.example.otushomework_01.tmdtb.Movie
import kotlin.random.Random

// Hollywood - random movie generator
object Hollywood {

    private val actors = arrayOf(
        "Тим Роббинс",
        "Боб Гантон",
        "Уильям Сэдлер",
        "Клэнси Браун",
        "Джил Беллоуз",
        "Марк Ролстон",
        "Джеймс Уитмор",
        "Джеффри ДеМанн",
        "Ларри Бранденбург",
        "Дэвид Морс",
        "Бонни Хант",
        "Майкл Кларк Дункан",
        "Джеймс Кромуэлл",
        "Майкл Джитер",
        "Грэм Грин",
        "Даг Хатчисон",
        "Сэм Рокуэлл",
        "Барри Пеппер",
        "Робин Райт",
        "Салли Филд",
        "Гэри Синиз",
        "Майкелти Уильямсон",
        "Майкл Коннер Хэмпфри",
        "Ханна Р. Холл",
        "Сэм Андерсон",
        "Шиван Фэллон",
        "Ребекка Уильямс",
        "Лиам Нисон",
        "Рэйф Файнс",
        "Кэролайн Гудолл",
        "Эмбет Дэвидц",
        "Йонатан Сэгаль",
        "Малгоша Гебель",
        "Шмуэль Леви",
        "Марк Иванир",
        "Беатриче Макола",
        "Франсуа Клюзе",
        "Омар Си",
        "Анн Ле Ни",
        "Одри Флеро",
        "Жозефин де Мо",
        "Клотильд Молле",
        "Альба Гайя Крагеде Беллуджи",
        "Сирил Менди",
        "Салимата Камате",
        "Абса Дьяту Тур",
        "Джозеф Гордон-Левитт",
        "Эллен Пейдж",
        "Том Харди",
        "Кэн Ватанабэ",
        "Дилип Рао",
        "Киллиан Мёрфи",
        "Том Беренджер",
        "Марион Котийяр",
        "Пит Постлетуэйт",
        "Жан Рено",
        "Натали Портман",
        "Дэнни Айелло",
        "Питер Эппел",
        "Уилли Уан Блад",
        "Дон Крич",
        "Кит А. Гласко",
        "Рэндольф Скотт",
        "Майкл Бадалукко",
        "Мэттью Бродерик",
        "Джереми Айронс",
        "Нэйтан Лейн",
        "Эрни Сабелла",
        "Джеймс Эрл Джонс",
        "Мойра Келли",
        "Роуэн Эткинсон",
        "Вупи Голдберг",
        "Чич Марин",
        "Никета Калам",
        "Хелена Бонем Картер",
        "Мит Лоаф",
        "Зэк Гренье",
        "Холт Маккэллани",
        "Джаред Лето",
        "Эйон Бэйли",
        "Ричмонд Аркетт",
        "Дэвид Эндрюс",
        "Юрий Яковлев",
        "Леонид Куравлёв",
        "Наталья Крачковская",
        "Савелий Крамаров",
        "Владимир Этуш",
        "Сергей Филиппов",
        "Наталья Кустинская",
        "Роберто Бениньи",
        "Николетта Браски",
        "Джорджио Кантарини",
        "Джустино Дурано",
        "Серджио Бини Бустрик",
        "Мариса Паредес",
        "Хорст Буххольц",
        "Лидия Альфонси",
        "Джулиана Лоджодиче",
        "Америго Фонтани",
        "Тиль Швайгер",
        "Ян Йозеф Лиферс",
        "Тьерри Ван Вервеке",
        "Мориц Бляйбтрой",
        "Хуб Стапель",
        "Леонард Лансинк",
        "Ральф Херфорт",
        "Корнелия Фробёсс",
        "Рутгер Хауэр",
        "Уилли Томчик",
        "Марлон Брандо",
        "Джеймс Каан",
        "Роберт Дювалл",
        "Ричард С. Кастеллано",
        "Дайан Китон",
        "Талия Шайр",
        "Джон Казале",
        "Аль Леттьери",
        "Стерлинг Хейден",
        "Джон Траволта",
        "Сэмюэл Л. Джексон",
        "Брюс Уиллис",
        "Ума Турман",
        "Винг Реймз",
        "Тим Рот",
        "Харви Кейтель",
        "Квентин Тарантино",
        "Питер Грин",
        "Аманда Пламмер",
        "Александр Демьяненко",
        "Наталья Селезнёва",
        "Алексей Смирнов",
        "Юрий Никулин",
        "Евгений Моргунов",
        "Георгий Вицин",
        "Михаил Пуговкин",
        "Виктор Павлов",
        "Владимир Басов",
        "Рина Зеленая",
        "Хью Джекман",
        "Пайпер Перабо",
        "Ребекка Холл",
        "Скарлетт Йоханссон",
        "Саманта Мэхурин",
        "Дэвид Боуи",
        "Дэниэл Дэвис",
        "Эд Харрис",
        "Дженнифер Коннелли",
        "Кристофер Пламмер",
        "Пол Беттани",
        "Адам Голдберг",
        "Джош Лукас",
        "Энтони Рэпп",
        "Джейсон Грей-Стенфорд",
        "Джадд Хёрш",
        "Мэттью МакКонахи",
        "Энн Хэтэуэй",
        "Джессика Честейн",
        "Маккензи Фой",
        "Дэвид Гяси",
        "Уэс Бентли",
        "Кейси Аффлек",
        "Джон Литгоу",
        "Элайджа Вуд",
        "Вигго Мортенсен",
        "Шон Эстин",
        "Иэн Маккеллен",
        "Доминик Монахэн",
        "Билли Бойд",
        "Энди Серкис",
        "Миранда Отто",
        "Рассел Кроу",
        "Хоакин Феникс",
        "Конни Нильсен",
        "Оливер Рид",
        "Ричард Харрис",
        "Дерек Джекоби",
        "Джимон Хонсу",
        "Дэвид Скофилд",
        "Джон Шрэпнел",
        "Томас Арана",
        "Джейсон Флеминг",
        "Декстер Флетчер",
        "Ник Моран",
        "Стивен Макинтош",
        "Николас Роу",
        "Ник Марк",
        "Чарльз Форбс",
        "Ленни МакЛин",
        "Киану Ривз",
        "Лоренс Фишбёрн",
        "Кэрри-Энн Мосс",
        "Хьюго Уивинг",
        "Глория Фостер",
        "Джо Пантольяно",
        "Маркус Чонг",
        "Джулиан Араханга",
        "Мэтт Доран",
        "Белинда МакКлори",
        "Мэтт Дэймон",
        "Марк Уолберг",
        "Рэй Уинстон",
        "Вера Фармига",
        "Энтони Андерсон",
        "Алек Болдуин",
        "Кевин Корригэн",
        "Том Хэнкс",
        "Кристофер Уокен",
        "Мартин Шин",
        "Натали Бай",
        "Эми Адамс",
        "Джеймс Бролин",
        "Брайан Хау",
        "Фрэнк Джон Хьюз",
        "Стив Истин",
        "Джейсон Стэйтем",
        "Стивен Грэм",
        "Брэд Питт",
        "Алан Форд",
        "Робби Ги",
        "Ленни Джеймс",
        "Эд",
        "Деннис Фарина",
        "Раде Шербеджия",
        "Винни Джонс",
        "Эдвард Нортон",
        "Эдвард Ферлонг",
        "Беверли Д’Анджело",
        "Дженнифер Лиен",
        "Итан Сапли",
        "Файруза Балк",
        "Эйвери Брукс",
        "Эллиотт Гулд",
        "Стейси Кич",
        "Уильям Расс",
        "Джонни Депп",
        "Джеффри Раш",
        "Орландо Блум",
        "Кира Найтли",
        "Джек Девенпорт",
        "Кевин МакНэлли",
        "Джонатан Прайс",
        "Ли Аренберг",
        "Макензи Крук",
        "Дэвид Бэйли",
        "Марк Руффало",
        "Бен Кингсли",
        "Макс фон Сюдов",
        "Мишель Уильямс",
        "Эмили Мортимер",
        "Патриша Кларксон",
        "Джеки Эрл Хейли",
        "Тед Левайн",
        "Джон Кэрролл Линч",
        "Кристиан Бэйл",
        "Хит Леджер",
        "Аарон Экхарт",
        "Мэгги Джилленхол",
        "Гари Олдман",
        "Майкл Кейн",
        "Морган Фриман",
        "Чинь Хань",
        "Нестор Карбонелл",
        "Эрик Робертс",
        "Джек Николсон",
        "Луиза Флетчер",
        "Уилл Сэмпсон",
        "Брэд Дуриф",
        "Уильям Редфилд",
        "Дэнни ДеВито",
        "Кристофер Ллойд",
        "Сидни Лэссик",
        "Нэйтан Джордж",
        "Винсент Скьявелли",
        "Леонардо ДиКаприо",
        "Кейт Уинслет",
        "Билли Зейн",
        "Кэти Бейтс",
        "Фрэнсис Фишер",
        "Глория Стюарт",
        "Билл Пэкстон",
        "Бернард Хилл",
        "Дэвид Уорнер",
        "Виктор Гарбер",
        "Аль Пачино",
        "Крис О’Доннелл",
        "Джеймс Ребхорн",
        "Габриель Анвар",
        "Филип Сеймур Хоффман",
        "Ричард Венчур",
        "Брэдли Уитфорд",
        "Рошель Оливер",
        "Маргарет Эджинтон",
        "Том Риис Фаррелл",
        "Ричард Гир",
        "Джоан Аллен",
        "Кэри-Хироюки Тагава",
        "Сара Ремер",
        "Джейсон Александер",
        "Эрик Авари",
        "Давиния МакФэдден",
        "Робби Саблетт",
        "Кевин ДеКосте",
        "Роб Деньян"
    )
    private val countries = arrayOf(
        "США",
        "Китай",
        "Япония",
        "Германия",
        "Великобритания",
        "Франция",
        "Индия Индия",
        "Италия",
        "Бразилия",
        "Республика Корея",
        "Канада",
        "Россия",
        "Испания",
        "Австралия",
        "Мексика",
        "Индонезия",
        "Нидерланды",
        "Саудовская Аравия",
        "Турция",
        "Швейцария",
        "Китайская Республика Тайвань",
        "Польша",
        "Швеция",
        "Бельгия",
        "Аргентина",
        "Таиланд",
        "Австрия",
        "Иран",
        "Норвегия",
        "ОАЭ",
        "Нигерия",
        "Ирландия",
        "Израиль",
        "ЮАР",
        "Сингапур",
        "Гонконг (КНР)",
        "Малайзия",
        "Дания",
        "Колумбия",
        "Филиппины",
        "Пакистан",
        "Чили",
        "Бангладеш",
        "Финляндия",
        "Египет",
        "Чехия",
        "Вьетнам",
        "Португалия",
        "Румыния",
        "Перу",
        "Ирак",
        "Греция"
    )
    private val genre = arrayOf(
        "аниме",
        "биографический",
        "боевик",
        "вестерн",
        "военный",
        "детектив",
        "детский",
        "документальный",
        "драма",
        "исторический",
        "кинокомикс",
        "комедия",
        "концерт",
        "короткометражный",
        "криминал",
        "мелодрама",
        "мистика",
        "музыка",
        "мультфильм",
        "мюзикл",
        "научный",
        "приключения",
        "реалити-шоу",
        "семейный",
        "спорт",
        "ток-шоу",
        "триллер",
        "ужасы",
        "фантастика",
        "фильм-нуар",
        "фэнтези",
        "эротика"
    )

    private val producer = arrayOf(
        "Акира Куросава",
        "Александр Петрович Довженко",
        "Александр Яковлевич Таиров",
        "Ален Рене",
        "Альфред Хичкок",
        "Анатолий Васильевич Эфрос",
        "Анджей Вайда",
        "Андре Антуан",
        "Андрей Арсеньевич Тарковский",
        "Андрей Сергеевич Михалков-Кончаловский",
        "Антонен Арто",
        "Бернардо Бертолуччи",
        "Вертов Дзига",
        "Вим Вендерс",
        "Витторио Де Сика",
        "Владимир Иванович Немирович-Данченко",
        "Владислав Александрович Старевич",
        "Всеволод Илларионович Пудовкин",
        "Всеволод Эмильевич Мейерхольд",
        "Вуди Аллен",
        "Георгий Александрович Товстоногов",
        "Говард Хоукс",
        "Гордон Крэг",
        "Григорий Васильевич Александров",
        "Григорий Михайлович Козинцев",
        "Джон Форд",
        "Джон Хьюстон",
        "Джордж Стрелер",
        "Дэвид Лин",
        "Дэвид Уорк Гриффит",
        "Евгений Багратионович Вахтангов",
        "Ежи Гротовский",
        "Жак Копо",
        "Жан Виаар",
        "Жан Виго",
        "Жан Ренуар",
        "Жан-Люк Годар",
        "Жорж Мельес",
        "Иван Александрович Пырьев",
        "Ингмар Бергман",
        "Карлдрейер",
        "Карлос Саура",
        "Квентин Тарантино",
        "Константин Сергеевич Станиславский",
        "Кэндзи Мидзогути",
        "Лев Абрамович Додин",
        "Лев Владимирович Кулешов",
        "Лени Рифеншталь",
        "Луис Вунюэль",
        "Лукино Висконти",
        "Мак Сеннетт",
        "Макс Рейнхардт",
        "Марк Анатольевич Захаров",
        "Марк Семенович Донской",
        "Марсель Карне",
        "Мартин Скорсезе",
        "Микеланджело Антониони",
        "Милош Форман",
        "Михаил Ильич Ромм",
        "Никита Сергеевич Михалков",
        "Орсон Уэллс",
        "Отар Иоселиани",
        "Петер Штайн",
        "Пискатор Эрвин",
        "Питер Брук",
        "Питер Гринуэй",
        "Пьер Пазолини",
        "Раинер Фасбиндер",
        "Рене Клер",
        "Робер Брессон",
        "Роберт Флаэрти",
        "Роберто Росселлини",
        "Роман Полански",
        "Сатьяджит Рей",
        "Сергей Аполлинариевич Герасимов",
        "Сергей Иосифович Параджанов",
        "Сергей Михайлович Эйзенштейн",
        "Сергей Федорович Бондарчук",
        "Стивен Спилберг",
        "Стэнли Кубрик",
        "Уильям Уайлер",
        "Уолт Дисней",
        "Федерико Феллини",
        "Франко Дзефиреали",
        "Франсуа Трюффо",
        "Фридрих Мурнау",
        "Фриц Ланг",
        "Фрэнк Капра",
        "Фрэнсис Коппола",
        "Чарлз Чаплин",
        "Шарль Дюллен",
        "Элиа Казан",
        "Эльдар Александрович Рязанов",
        "Эмир Кустурица",
        "Эмир Кустурица",
        "Эрих Фон Штрогейм",
        "Эрнст Любич",
        "Юрий Петрович Любимов",
        "Ясудзиро Одзу"
    )

    fun convertMovieToItem(movie: Movie) : MovieItem {
        return MovieItem(
            "https://image.tmdb.org/t/p/w342${movie.posterPath}",
            "https://image.tmdb.org/t/p/w780${movie.backdropPath}",
            movie.title,
            randDetails(),
            movie.overview
        )
    }

    private fun randDetails() : String {
        return "%d, %s\n%s\n%d ч %d мин\nРежиссер: %s\nВ главных ролях: %s".format(
            1990 + Random.nextInt(30),
            pickFrom(
                countries,
                Random.nextInt(1, 3)
            ),
            pickFrom(
                genre,
                if (Random.nextInt(10) > 2) 2 else 1
            ),
            Random.nextInt(1,2),
            Random.nextInt(0,60),
            producer.random(),
            pickFrom(
                actors,
                Random.nextInt(2, 5)
            )
        )
    }

    private fun pickFrom(strings: Array<String>, n: Int) : String {
        if (n <= 0) {
            return ""
        }
        else {
            val idx = ArrayList<Int>()

            while (idx.size < n)
            {
                val r = Random.nextInt(strings.size)
                if (!idx.contains(r))
                    idx.add(r)
            }

            val sb = StringBuilder()

            sb.append(strings[idx[0]])
            for (i in 1 until n) {
                sb.append(", ")
                sb.append(strings[idx[i]])
            }
            return sb.toString()
        }
    }
}