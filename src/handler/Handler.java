package handler;

import javax.servlet.http.HttpServletRequest;

/**
 * mvcにおけるHandlerパッケージの抽象クラス.
 * @author リコーITソリューションズ株式会社 z00s600124
 *
 */
public interface Handler {

    /**
     * 画面に応じたサービス処理を実行する. 入力チェック、モデルの作成と実行
     *
     * @param request 画面から入力された内容を保持しているオブジェクト
     * @return サービスを実行した結果に応じたViewの名前
     */
    //これはpublic
    String handleService(HttpServletRequest request);

}
