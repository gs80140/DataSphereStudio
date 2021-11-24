/*
 * Copyright 2019 WeBank
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.webank.wedatasphere.dss.workflow.restful;

import com.webank.wedatasphere.dss.common.exception.DSSErrorException;
import com.webank.wedatasphere.dss.workflow.cs.service.CSTableService;
import com.webank.wedatasphere.dss.workflow.entity.request.QueryTableMetaRequest;
import com.webank.wedatasphere.dss.workflow.entity.request.TablesRequest;
import org.apache.linkis.server.Message;
import org.apache.linkis.server.security.SecurityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

@RequestMapping(path = "/dss/workflow", produces = {"application/json"})
@RestController
public class ContextServiceRestful {

    @Autowired
    private CSTableService csTableService;

    @RequestMapping(value = "tables",method = RequestMethod.POST)
    public Message tables(@Context HttpServletRequest req, @RequestBody TablesRequest tablesRequest) throws DSSErrorException {
        String userName = SecurityFilter.getLoginUsername(req);
        String contextIDStr = tablesRequest.getContextID();
        String nodeName = tablesRequest.getNodeName();
        return Message.ok().data("tables", csTableService.queryTables("default", contextIDStr, nodeName));
    }


    @RequestMapping(value = "columns",method = RequestMethod.POST)
    public Message queryTableMeta(@Context HttpServletRequest req,@RequestBody QueryTableMetaRequest queryTableMetaRequest) throws DSSErrorException {
        String userName = SecurityFilter.getLoginUsername(req);
        String contextIDStr = queryTableMetaRequest.getContextID();
        String contextKeyStr = queryTableMetaRequest.getContextKey();
        return Message.ok().data("columns", csTableService.queryTableMeta("default", contextIDStr, contextKeyStr));
    }

}
