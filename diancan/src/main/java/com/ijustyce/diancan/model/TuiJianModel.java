package com.ijustyce.diancan.model;

import com.ijustyce.fastandroiddev.net.IResponseData;
import java.util.List;

/**
 * Created by yc on 16-4-29.    推荐
 */
public class TuiJianModel extends IResponseData<TuiJianItem> {

    private DataEntity data;

    public List<TuiJianItem> getData() {
        return data.getList();
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {

        private List<TuiJianItem> list;

        public List<TuiJianItem> getList() {
            return list;
        }

        public void setList(List<TuiJianItem> list) {
            this.list = list;
        }
    }
}
