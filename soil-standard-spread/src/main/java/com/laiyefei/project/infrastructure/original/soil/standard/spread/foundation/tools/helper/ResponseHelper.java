package com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.tools.helper;


import com.laiyefei.project.infrastructure.original.soil.standard.foundation.utensil.helper.IHelper;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.tools.util.FileUtil;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.tools.util.StringUtil;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.tools.util.ZipUtil;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.pojo.co.EncodeType;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-04-06 14:59
 * @Desc : this is class named ResponseHelper for do ResponseHelper
 * @Version : v2.0.0.20200406
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public final class ResponseHelper implements IHelper {

    private final HttpServletResponse response;
    private final byte[] data;

    public ResponseHelper(HttpServletResponse response, byte[] data) {
        this.response = response;
        this.data = data;
    }

    public final ResponseHelper adornResponse(final String fileName) throws UnsupportedEncodingException {
        this.response.reset();
        this.response.setHeader(
                "Content-Disposition",
                "attachment; filename=".concat(URLEncoder.encode(fileName, EncodeType.UTF8)).concat(".zip"));
        this.response.addHeader("Content-Length", String.valueOf(this.data.length));
        this.response.setContentType("application/octet-stream; charset=UTF-8");
        return this;
    }

    public final void export() throws IOException {
        IOUtils.write(this.data, this.response.getOutputStream());
    }

    public static final ResponseHelper BuildBy(HttpServletResponse response, byte[] data) {
        return new ResponseHelper(response, data);
    }

    public static final void Download(final HttpServletResponse response,
                                      final String path) throws IOException {
        ResponseHelper.BuildBy(response, ZipUtil.pack(
                FileHelper.BuildBy(path).getPathContainer().toArray(new String[0]), path)).adornResponse(FileUtil.GainFileNameByPath(path));
    }

    public static final void Download(final HttpServletResponse response,
                                      final String path,
                                      final String version) throws IOException {
        ResponseHelper.BuildBy(response, ZipUtil.pack(FileHelper.BuildBy(path).getPathContainer(version).toArray(new String[0]), path))
                .adornResponse(FileUtil.GainFileNameByPath(path, StringUtil.MIDDLE_LINE, version)).export();
    }

    public static final void Download(final HttpServletResponse response,
                                      final byte[] datas,
                                      final String downloadName) throws IOException {
        ResponseHelper.BuildBy(response, datas).adornResponse(downloadName).export();
    }

}
