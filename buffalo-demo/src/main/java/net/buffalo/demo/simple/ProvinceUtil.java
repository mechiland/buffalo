/*
 * Copyright 2002-2004 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * $Id: ProvinceUtil.java,v 1.1 2006/01/07 03:26:52 mechiland Exp $
 */ 
package net.buffalo.demo.simple;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author michael
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ProvinceUtil {
    
    private static List provinces = null;
    
    public static List provinces() {
        if (provinces != null) return provinces;
        provinces = new LinkedList();
        provinces.add(new Province("��ѡ��ʡ����","��ѡ�������"));
        provinces.add( new Province("����","����|����|����|����|����|��̨|ʯ��ɽ|����|��ͷ��|��ɽ|ͨ��|˳��|��ƽ|����|ƽ��|����|����|����")); 
        provinces.add( new Province("�Ϻ�","����|¬��|���|����|����|����|բ��|���|����|����|��ɽ|�ζ�|�ֶ�|��ɽ|�ɽ�|����|�ϻ�|����|����")); 
        provinces.add( new Province("���","��ƽ|����|�Ӷ�|����|����|����|�Ͽ�|����|�ӱ�|����|����|����|����|���|����|����|����|����")); 
        provinces.add( new Province("����","����|����|����|��ɿ�|����|ɳƺ��|������|�ϰ�|����|��ʢ|˫��|�山|����|ǭ��|����|�뽭|����|ͭ��|����|�ٲ�|��ɽ|��ƽ|�ǿ�|�ᶼ|�潭|��¡|����|����|����|���|��ɽ|��Ϫ|ʯ��|��ɽ|����|��ˮ|����|�ϴ�|����|�ϴ�")); 
        provinces.add( new Province("�ӱ�","ʯ��ׯ|����|��̨|����|�żҿ�|�е�|�ȷ�|��ɽ|�ػʵ�|����|��ˮ")); 
        provinces.add( new Province("ɽ��","̫ԭ|��ͬ|��Ȫ|����|����|˷��|����|����|����|�ٷ�|�˳�")); 
        provinces.add( new Province("���ɹ�","���ͺ���|��ͷ|�ں�|���|���ױ�����|��������|����ľ��|�˰���|�����첼��|���ֹ�����|�����׶���|��������")); 
        provinces.add( new Province("����","����|����|��ɽ|��˳|��Ϫ|����|����|Ӫ��|����|����|�̽�|����|����|��«��")); 
        provinces.add( new Province("����","����|����|��ƽ|��Դ|ͨ��|��ɽ|��ԭ|�׳�|�ӱ�")); 
        provinces.add( new Province("������","������|�������|ĵ����|��ľ˹|����|�绯|�׸�|����|�ں�|˫Ѽɽ|����|��̨��|���˰���")); 
        provinces.add( new Province("����","�Ͼ�|��|����|��ͨ|����|�γ�|����|���Ƹ�|����|����|��Ǩ|̩��|����")); 
        provinces.add( new Province("�㽭","����|����|����|����|����|����|��|����|��ɽ|̨��|��ˮ")); 
        provinces.add( new Province("����","�Ϸ�|�ߺ�|����|��ɽ|����|ͭ��|����|��ɽ|����|����|����|����|����|����|����|����|����")); 
        provinces.add( new Province("����","����|����|����|����|Ȫ��|����|��ƽ|����|����")); 
        provinces.add( new Province("����","�ϲ���|������|�Ž�|ӥ̶|Ƽ��|����|����|����|�˴�|����|����")); 
        provinces.add( new Province("ɽ��","����|�ൺ|�Ͳ�|��ׯ|��Ӫ|��̨|Ϋ��|����|̩��|����|����|����|����|����|�ĳ�|����|����")); 
        provinces.add( new Province("����","֣��|����|����|ƽ��ɽ|����|�ױ�|����|����|���|���|���|����Ͽ|����|����|����|�ܿ�|פ���|��Դ")); 
        provinces.add( new Province("����","�人|�˲�|����|�差|��ʯ|����|�Ƹ�|ʮ��|��ʩ|Ǳ��|����|����|����|����|Т��|����"));
        provinces.add( new Province("����","��ɳ|����|����|��̶|����|����|����|����|¦��|����|����|����|����|�żҽ�")); 
        provinces.add( new Province("�㶫","����|����|�麣|��ͷ|��ݸ|��ɽ|��ɽ|�ع�|����|տ��|ï��|����|����|÷��|��β|��Դ|����|��Զ|����|����|�Ƹ�")); 
        provinces.add( new Province("����","����|����|����|����|����|���Ǹ�|����|���|����|��������|���ݵ���|����|��ɫ|�ӳ�")); 
        provinces.add( new Province("����","����|����")); 
        provinces.add( new Province("�Ĵ�","�ɶ�|����|����|�Թ�|��֦��|��Ԫ|�ڽ�|��ɽ|�ϳ�|�˱�|�㰲|�ﴨ|�Ű�|üɽ|����|��ɽ|����")); 
        provinces.add( new Province("����","����|����ˮ|����|��˳|ͭ��|ǭ����|�Ͻ�|ǭ����|ǭ��")); 
        provinces.add( new Province("����","����|����|����|��Ϫ|��ͨ|����|���|��ɽ|˼é|��˫����|��ɽ|�º�|����|ŭ��|����|�ٲ�"));
        provinces.add( new Province("����","����|�տ���|ɽ��|��֥|����|����|����")); 
        provinces.add( new Province("����","����|����|����|ͭ��|μ��|�Ӱ�|����|����|����|����")); 
        provinces.add( new Province("����","����|������|���|����|��ˮ|��Ȫ|��Ҵ|����|����|¤��|ƽ��|����|����|����")); 
        provinces.add( new Province("����","����|ʯ��ɽ|����|��ԭ")); 
        provinces.add( new Province("�ຣ","����|����|����|����|����|����|����|����")); 
        provinces.add( new Province("�½�","��³ľ��|ʯ����|��������|����|��������|����|�������տ¶�����|��������|��³��|����|��ʲ|����|������")); 
        provinces.add( new Province("���","")); 
        provinces.add( new Province("����","")); 
        provinces.add( new Province("̨��","̨��|����|̨��|̨��|����|��Ͷ|����|����|�û�|����|����|����|��԰|����|��¡|̨��|����|����|���")); 
        provinces.add( new Province("����","������|������|����|����|ŷ��|������")); 
        
        return provinces;
    }
    
    public static Province getProvince(String name) {
        List list = provinces();
        Province p = null;
        for (int i = 0; i < list.size(); i++) {
            p = (Province)(list.get(i));
            if (name.equals(p.getName())) break;
        }
        
        return p;
    }
    
    public static List provinceNames() {
        List list = provinces();
        List names = new ArrayList();
        Province p = null;
        for (int i = 0; i < list.size(); i++) {
            p = (Province)(list.get(i));
            names.add(p.getName());
        }
        
        return names;
    }
    
}
