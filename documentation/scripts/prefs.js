Prefs={};

/*===== [ Paths ] =====*/
Prefs.dataRootUrl="data/";
Prefs.pagesListUrl="data.xml";

/*===== [ Parameters ] =====*/
Prefs.defaultLang="Java";
Prefs.contentType="wiki";

/*===== [ Navigation ] =====*/
Prefs.navigation={};
Prefs.navigation.maxHistoryLength=20;
Prefs.navigation.homePageUrl="data/home.txt";
Prefs.navigation.page404Url="data/404.txt";
Prefs.navigation.lastPageUrl="data/home.txt";

/*===== [ Search ] =====*/
Prefs.search={};

Prefs.search.caseSensitiveSearch=false;
Prefs.search.searchOnlyInTitles=true;

/*===== [ Appearance ] =====*/
Prefs.appearance={};

Prefs.appearance.defaultColors="Default";
Prefs.appearance.available=["Default", "Dark"];
Prefs.appearance.openLastViewed=true;

Prefs.appearance.text={};

Prefs.appearance.text.themes="Цветовые темы";
Prefs.appearance.text.openLastViewed="Запоминать последнюю страницу";

/*===== [ Strings ] =====*/
Prefs.text={};

Prefs.text.loading="Загрузка...";
Prefs.text.initialContent="Выберите любой раздел в списке слева...";
Prefs.text.initialRibbonContent="Панель загружается...";
Prefs.text.ribbonListDefaultElementContent="(пусто)";
Prefs.text.versionDefaultName="(без имени)";
Prefs.text.obsoleteVersionWarning="Вы просматриваете устаревшую версию!";

Prefs.navigation.text={};
Prefs.navigation.text.scrollToTop="Наверх";
Prefs.navigation.text.scrollToTopIcon="&#8682;";

Prefs.navigation.text.goBack="Назад";
Prefs.navigation.text.goBackIcon="&#9666;";
Prefs.navigation.text.goForward="Вперёд";
Prefs.navigation.text.goForwardIcon="&#9656;";

Prefs.navigation.text.browseHistory="История";
Prefs.navigation.text.browseHistoryIcon="&#8986;";

Prefs.navigation.text.homePage="Домой";
Prefs.navigation.text.homePageIcon="&#8962;";


Prefs.navigation.text.historyNavSection="Журнал просмотра";
Prefs.navigation.text.searchNavSection="Поиск";

Prefs.navigation.text.moreVersionsLabel="...";

Prefs.search.text={};
Prefs.search.text.nothingWasFound="(ничего не найдено)";
Prefs.search.text.search="Поиск &#x27a4;";
Prefs.search.text.searchIcon="";
Prefs.search.text.caseSensitive="Учесть регистр";
Prefs.search.text.onlyInTitles="Только в заголовках";
Prefs.search.text.searchInvitation="Искать...";


/* functionality */

Prefs.save=function()
{
	setCookie("defColors", Prefs.appearance.defaultColors, {expires:6*30*24*60*60});
	setCookie("openLastViewed", Prefs.appearance.openLastViewed? "1":"0", {expires:6*30*24*60*60});
	setCookie("lastPageUrl", Prefs.navigation.lastPageUrl, {expires:6*30*24*60*60});
}

Prefs.load=function()
{
	defColors=getCookie("defColors");
	if(defColors!=null) Prefs.appearance.defaultColors=defColors;
	
	svLW=getCookie("openLastViewed");
	if(svLW!=null) Prefs.appearance.openLastViewed = (svLW=="1");
	
	lpU=getCookie("lastPageUrl");
	if(lpU!=null) Prefs.navigation.lastPageUrl=lpU;
}

Prefs.load();