package utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import beans.StoreBean;
import beans.StoreImages;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class CommonImageFileManager {
	private HttpServletRequest req;
	private DiskFileItemFactory fileManager;
	ServletFileUpload servletFile;
	
	
	public CommonImageFileManager(HttpServletRequest req) {
		this.req = req;
		this.fileManager = new DiskFileItemFactory();
		this.servletFile = new ServletFileUpload(fileManager);
		this.servletFile.setSizeMax(10*1024*1024);
	}

	public String searchStoreIdx() {
		String storeCode = null;
		
		return storeCode;
	}
	
	public ArrayList<StoreImages> saveGoodsImageFile(StoreBean store, String groupCode, boolean isThumbnail) {
		//String savePath = this.session.getServletContext().getRealPath("/resources/images/" + groupCode);
		String savePath = "D:\\Dynamic_JS\\CodeSpace\\web-pos\\src\\main\\webapp\\resources\\images\\"+ groupCode;
		/* StoreImages 생성 */
		ArrayList<StoreImages> storeImageList = null;
		StoreImages storeImages = null;
		/* group folder가 없다면 생성 */
		File f = new File(savePath);
		if(!f.exists()) f.mkdir();
		
		try {
			List<FileItem> files = this.servletFile.parseRequest(this.req);
			
			for(FileItem file : files) {
				if(file.isFormField()) {
					switch(file.getFieldName()) {
					case "goodsCode" : store.getGoodsList().get(store.getGoodsList().size()-1).setGoodsCode(file.getString("UTF-8")); break;
					case "goodsName": store.getGoodsList().get(store.getGoodsList().size()-1).setGoodsName(file.getString("UTF-8")); break;
					case "goodsCategoryCode": store.getGoodsList().get(store.getGoodsList().size()-1).setGoodsCategoryCode(file.getString("UTF-8"));break;
					case "goodsStateCode": store.getGoodsList().get(store.getGoodsList().size()-1).setGoodsStateCode(file.getString("UTF-8"));break;
					case "goodsColorCode": store.getGoodsList().get(store.getGoodsList().size()-1).setGoodsColorCode(file.getString("UTF-8"));break;
					}
				}else {
					if(file.getFieldName().equals("goodsImageCode")) {
						/* 파일정보수집 << 전송순서가 파일보다 상품코드가 우선되어야 함 */
						String goodsImageCode = store.getStoreCode() + store.getGoodsList().get(store.getGoodsList().size()-1).getGoodsCode() + "B";
						store.getGoodsList().get(store.getGoodsList().size()-1).setGoodsImageCode(goodsImageCode);
						String fileExt = "." + file.getName().split("\\.")[1];
						
						/* StoreImage Table 저장 데이터 */
						storeImageList = new ArrayList<StoreImages>();
						storeImages = new StoreImages();
						storeImages.setImageCode(goodsImageCode);
						storeImages.setImageDisplayName(store.getGoodsList().get(store.getGoodsList().size()-1).getGoodsName());
						storeImages.setImageLocation(savePath + "\\" + goodsImageCode + fileExt );
						storeImageList.add(storeImages);
						/* 파일저장 */
						f = new File(savePath, goodsImageCode + fileExt);
						file.write(f);
						/* Thumbnail 생성 여부에 따라 */
						String rootFile = savePath + goodsImageCode + fileExt;
						if(isThumbnail) {
							//this.createThumbnailImage(storeImageList, rootFile);
						}
					}
				}
			}
		} 
		catch (FileUploadException e) {e.printStackTrace();} 
		catch (Exception e) {e.printStackTrace();}
		return storeImageList;
	}
	
	private void createThumbnailImage(ArrayList<StoreImages> storeImageList, String rootFile) {
		
		int[][] thumbImageSize = {{250, 150},{80, 48}};
		int[][] cropImageSize = new int[1][2];
		String[] thumbFilePath = {rootFile.replace("B", "N"), rootFile.replace("B", "S")};
		try {
			BufferedImage bufferImage = ImageIO.read(new File(rootFile));
			int[] rootSize = {bufferImage.getWidth(), bufferImage.getHeight()};
			 /* 너비를 기준으로 높이를 계산 */
			cropImageSize[0][0] = rootSize[0];
			cropImageSize[0][1] = (rootSize[0] * thumbImageSize[0][1]) / thumbImageSize[0][0];
			/* 계산된 이미지 높이가 원본보다 크다면 반대비율로 계산 */
			if(cropImageSize[0][1] > rootSize[1]) {
				cropImageSize[0][0] = (rootSize[1] * thumbImageSize[0][0]) / thumbImageSize[0][1];
				cropImageSize[0][1] = rootSize[1];
			}
			
			/* cropImage 만들기 */
			
			Thumbnails.of(thumbFilePath[0])
					.crop(Positions.CENTER)
					.size(cropImageSize[0][0], cropImageSize[0][1])
					.toFile(new File(thumbFilePath[1]));
			
			
		} catch (IOException e) {e.printStackTrace();}
	}
}
