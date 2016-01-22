package test.Dao;

import java.util.List;

import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;
import com.baidu.yun.push.auth.PushKeyPair;
import com.baidu.yun.push.client.BaiduPushClient;
import com.baidu.yun.push.constants.BaiduPushConstants;
import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import com.baidu.yun.push.model.AddDevicesToTagRequest;
import com.baidu.yun.push.model.AddDevicesToTagResponse;
import com.baidu.yun.push.model.DeviceInfo;

public class AddDevicesToTag {
	public static void main(String[] args) throws PushClientException,
			PushServerException {
		// 1. get apiKey and secretKey from developer console
		String apiKey = "xxxxxxxxxxxxxxxxxxxx";
		String secretKey = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
		PushKeyPair pair = new PushKeyPair(apiKey, secretKey);

		// 2. build a BaidupushClient object to access released interfaces
		BaiduPushClient pushClient = new BaiduPushClient(pair,
				BaiduPushConstants.CHANNEL_REST_URL);

		// 3. register a YunLogHandler to get detail interacted information
		// in this request.
		pushClient.setChannelLogHandler(new YunLogHandler() {
			@Override
			public void onHandle(YunLogEvent event) {
				System.out.println(event.getMessage());
			}
		});

		try {
			// 4. specify request arguments
			String[] channelIds = { "xxxxxxxxxxxxxxxxx" };
			AddDevicesToTagRequest request = new AddDevicesToTagRequest()
					.addTagName("xxxxx").addChannelIds(channelIds)
					.addDeviceType(3);
			// 5. http request
			AddDevicesToTagResponse response = pushClient
					.addDevicesToTag(request);
			// Http请求结果解析打印
			if (null != response) {
				StringBuilder strBuilder = new StringBuilder();
				strBuilder.append("devicesInTag：{");
				List<?> devicesInfo = response.getDevicesInfoAfterAdded();
				for (int i = 0; i < devicesInfo.size(); i++) {
					Object object = devicesInfo.get(i);
					if (i != 0) {
						strBuilder.append(",");
					}
					if (object instanceof DeviceInfo) {
						DeviceInfo deviceInfo = (DeviceInfo) object;
						strBuilder.append("{channelId:"
								+ deviceInfo.getChannelId() + ",result:"
								+ deviceInfo.getResult() + "}");
					}
				}
				strBuilder.append("}");
				System.out.println(strBuilder.toString());
			}
		} catch (PushClientException e) {
			if (BaiduPushConstants.ERROROPTTYPE) {
				throw e;
			} else {
				e.printStackTrace();
			}
		} catch (PushServerException e) {
			if (BaiduPushConstants.ERROROPTTYPE) {
				throw e;
			} else {
				System.out.println(String.format(
						"requestId: %d, errorCode: %d, errorMessage: %s",
						e.getRequestId(), e.getErrorCode(), e.getErrorMsg()));
			}
		}
	}
}
