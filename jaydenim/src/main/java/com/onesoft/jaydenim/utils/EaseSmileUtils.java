/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.onesoft.jaydenim.utils;

import android.content.Context;
import android.net.Uri;
import android.text.Spannable;
import android.text.Spannable.Factory;
import android.text.style.ImageSpan;

import com.onesoft.jaydenim.EaseUI;
import com.onesoft.jaydenim.domain.EaseEmojicon;
import com.onesoft.jaydenim.model.EaseDefaultEmojiconDatas;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EaseSmileUtils {
    public static final String DELETE_KEY = "em_delete_delete_expression";
    
	public static final String ee_0 = "[Emo]0.gif[//]0[/Emo]";
	public static final String ee_1 = "[Emo]1.gif[//]0[/Emo]";
	public static final String ee_2 = "[Emo]2.gif[//]0[/Emo]";
	public static final String ee_3 = "[Emo]3.gif[//]0[/Emo]";
	public static final String ee_4 = "[Emo]4.gif[//]0[/Emo]";
	public static final String ee_5 = "[Emo]5.gif[//]0[/Emo]";
	public static final String ee_6 = "[Emo]6.gif[//]0[/Emo]";
	public static final String ee_7 = "[Emo]7.gif[//]0[/Emo]";
	public static final String ee_8 = "[Emo]8.gif[//]0[/Emo]";
	public static final String ee_9 = "[Emo]9.gif[//]0[/Emo]";
	public static final String ee_10 = "[Emo]10.gif[//]0[/Emo]";
	public static final String ee_11 = "[Emo]11.gif[//]0[/Emo]";
	public static final String ee_12 = "[Emo]12.gif[//]0[/Emo]";
	public static final String ee_13 = "[Emo]13.gif[//]0[/Emo]";
	public static final String ee_14 = "[Emo]14.gif[//]0[/Emo]";
	public static final String ee_15 = "[Emo]15.gif[//]0[/Emo]";
	public static final String ee_16 = "[Emo]16.gif[//]0[/Emo]";
	public static final String ee_17 = "[Emo]17.gif[//]0[/Emo]";
	public static final String ee_18 = "[Emo]18.gif[//]0[/Emo]";
	public static final String ee_19 = "[Emo]19.gif[//]0[/Emo]";
	public static final String ee_20 = "[Emo]20.gif[//]0[/Emo]";
	public static final String ee_21 = "[Emo]21.gif[//]0[/Emo]";
	public static final String ee_22 = "[Emo]22.gif[//]0[/Emo]";
	public static final String ee_23 = "[Emo]23.gif[//]0[/Emo]";
	public static final String ee_24 = "[Emo]24.gif[//]0[/Emo]";
	public static final String ee_25 = "[Emo]25.gif[//]0[/Emo]";
	public static final String ee_26 = "[Emo]26.gif[//]0[/Emo]";
	public static final String ee_27 = "[Emo]27.gif[//]0[/Emo]";
	public static final String ee_28 = "[Emo]28.gif[//]0[/Emo]";
	public static final String ee_29 = "[Emo]29.gif[//]0[/Emo]";
	public static final String ee_30 = "[Emo]30.gif[//]0[/Emo]";
	public static final String ee_31 = "[Emo]31.gif[//]0[/Emo]";
	public static final String ee_32 = "[Emo]32.gif[//]0[/Emo]";
	public static final String ee_33 = "[Emo]33.gif[//]0[/Emo]";
	public static final String ee_34 = "[Emo]34.gif[//]0[/Emo]";
	public static final String ee_35 = "[Emo]35.gif[//]0[/Emo]";
	public static final String ee_36 ="[Emo]36.gif[//]0[/Emo]";
	public static final String ee_37 ="[Emo]37.gif[//]0[/Emo]";
	public static final String ee_38 ="[Emo]38.gif[//]0[/Emo]";
	public static final String ee_39 ="[Emo]39.gif[//]0[/Emo]";
	public static final String ee_40 ="[Emo]40.gif[//]0[/Emo]";
	public static final String ee_41 ="[Emo]41.gif[//]0[/Emo]";
	public static final String ee_42 ="[Emo]42.gif[//]0[/Emo]";
	public static final String ee_43 ="[Emo]43.gif[//]0[/Emo]";
	public static final String ee_44 ="[Emo]44.gif[//]0[/Emo]";
	public static final String ee_45 ="[Emo]45.gif[//]0[/Emo]";
	public static final String ee_46 ="[Emo]46.gif[//]0[/Emo]";
	public static final String ee_47 ="[Emo]47.gif[//]0[/Emo]";
	public static final String ee_48 ="[Emo]48.gif[//]0[/Emo]";
	public static final String ee_49 ="[Emo]49.gif[//]0[/Emo]";
	public static final String ee_50 ="[Emo]50.gif[//]0[/Emo]";
	public static final String ee_51 ="[Emo]51.gif[//]0[/Emo]";
	public static final String ee_52 ="[Emo]52.gif[//]0[/Emo]";
	public static final String ee_53 ="[Emo]53.gif[//]0[/Emo]";
	public static final String ee_54 ="[Emo]54.gif[//]0[/Emo]";
	public static final String ee_55 ="[Emo]55.gif[//]0[/Emo]";
	public static final String ee_56 ="[Emo]56.gif[//]0[/Emo]";
	public static final String ee_57 ="[Emo]57.gif[//]0[/Emo]";
	public static final String ee_58 ="[Emo]58.gif[//]0[/Emo]";
	public static final String ee_59 ="[Emo]59.gif[//]0[/Emo]";
	public static final String ee_60 ="[Emo]60.gif[//]0[/Emo]";
	public static final String ee_61 ="[Emo]61.gif[//]0[/Emo]";
	public static final String ee_62 ="[Emo]62.gif[//]0[/Emo]";
	public static final String ee_63 ="[Emo]63.gif[//]0[/Emo]";
	public static final String ee_64 ="[Emo]64.gif[//]0[/Emo]";
	public static final String ee_65 ="[Emo]65.gif[//]0[/Emo]";
	public static final String ee_66 ="[Emo]66.gif[//]0[/Emo]";
	public static final String ee_67 ="[Emo]67.gif[//]0[/Emo]";
	public static final String ee_68 ="[Emo]68.gif[//]0[/Emo]";
	public static final String ee_69 ="[Emo]69.gif[//]0[/Emo]";
	public static final String ee_70 ="[Emo]70.gif[//]0[/Emo]";
	public static final String ee_71 ="[Emo]71.gif[//]0[/Emo]";
	public static final String ee_72 ="[Emo]72.gif[//]0[/Emo]";
	public static final String ee_73 ="[Emo]73.gif[//]0[/Emo]";
	public static final String ee_74 ="[Emo]74.gif[//]0[/Emo]";
	public static final String ee_75 ="[Emo]75.gif[//]0[/Emo]";
	public static final String ee_76 ="[Emo]76.gif[//]0[/Emo]";
	public static final String ee_77 ="[Emo]77.gif[//]0[/Emo]";
	public static final String ee_78 ="[Emo]78.gif[//]0[/Emo]";
	public static final String ee_79 ="[Emo]79.gif[//]0[/Emo]";
	public static final String ee_80 ="[Emo]80.gif[//]0[/Emo]";
	public static final String ee_81 ="[Emo]81.gif[//]0[/Emo]";
	public static final String ee_82 ="[Emo]82.gif[//]0[/Emo]";
	public static final String ee_83 ="[Emo]83.gif[//]0[/Emo]";
	public static final String ee_84 ="[Emo]84.gif[//]0[/Emo]";
	public static final String ee_85 ="[Emo]85.gif[//]0[/Emo]";
	public static final String ee_86 ="[Emo]86.gif[//]0[/Emo]";
	public static final String ee_87 ="[Emo]87.gif[//]0[/Emo]";
	public static final String ee_88 ="[Emo]88.gif[//]0[/Emo]";
	public static final String ee_89 ="[Emo]89.gif[//]0[/Emo]";
	public static final String ee_90 ="[Emo]90.gif[//]0[/Emo]";
	public static final String ee_91 ="[Emo]91.gif[//]0[/Emo]";
	public static final String ee_92 ="[Emo]92.gif[//]0[/Emo]";
	public static final String ee_93 ="[Emo]93.gif[//]0[/Emo]";
	public static final String ee_94 ="[Emo]94.gif[//]0[/Emo]";
	public static final String ee_95 ="[Emo]95.gif[//]0[/Emo]";
	public static final String ee_96 ="[Emo]96.gif[//]0[/Emo]";
	public static final String ee_97 ="[Emo]97.gif[//]0[/Emo]";
	public static final String ee_98 ="[Emo]98.gif[//]0[/Emo]";
	public static final String ee_99 ="[Emo]99.gif[//]0[/Emo]";
	public static final String ee_100 ="[Emo]100.gif[//]0[/Emo]";
	public static final String ee_101 ="[Emo]101.gif[//]0[/Emo]";
	public static final String ee_102 ="[Emo]102.gif[//]0[/Emo]";
	public static final String ee_103 ="[Emo]103.gif[//]0[/Emo]";
	public static final String ee_104 ="[Emo]104.gif[//]0[/Emo]";

	private static final Factory spannableFactory = Factory
	        .getInstance();
	
	private static final Map<Pattern, Object> emoticons = new HashMap<Pattern, Object>();
	

	static {
	    EaseEmojicon[] emojicons = EaseDefaultEmojiconDatas.getData();
		for (EaseEmojicon emojicon : emojicons) {
			addPattern(emojicon.getEmojiText(), emojicon.getIcon());
		}
	    EaseUI.EaseEmojiconInfoProvider emojiconInfoProvider = EaseUI.getInstance().getEmojiconInfoProvider();
	    if(emojiconInfoProvider != null && emojiconInfoProvider.getTextEmojiconMapping() != null){
	        for(Entry<String, Object> entry : emojiconInfoProvider.getTextEmojiconMapping().entrySet()){
	            addPattern(entry.getKey(), entry.getValue());
	        }
	    }
	    
	}

	/**
	 * add text and icon to the map
	 * @param emojiText-- text of emoji
	 * @param icon -- resource id or local path
	 */
	public static void addPattern(String emojiText, Object icon){
	    emoticons.put(Pattern.compile(Pattern.quote(emojiText)), icon);
	}

	/**
	 * replace existing spannable with smiles
	 * @param context
	 * @param spannable
	 * @return
	 */
	public static boolean addSmiles(Context context, Spannable spannable) {
	    boolean hasChanges = false;
	    for (Entry<Pattern, Object> entry : emoticons.entrySet()) {
	        Matcher matcher = entry.getKey().matcher(spannable);
	        while (matcher.find()) {
	            boolean set = true;
	            for (ImageSpan span : spannable.getSpans(matcher.start(),
	                    matcher.end(), ImageSpan.class))
	                if (spannable.getSpanStart(span) >= matcher.start()
	                        && spannable.getSpanEnd(span) <= matcher.end())
	                    spannable.removeSpan(span);
	                else {
	                    set = false;
	                    break;
	                }
	            if (set) {
	                hasChanges = true;
	                Object value = entry.getValue();
	                if(value instanceof String && !((String) value).startsWith("http")){
	                    File file = new File((String) value);
	                    if(!file.exists() || file.isDirectory()){
	                        return false;
	                    }
	                    spannable.setSpan(new ImageSpan(context, Uri.fromFile(file)),
	                            matcher.start(), matcher.end(),
	                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	                }else{
	                    spannable.setSpan(new ImageSpan(context, (Integer)value),
	                            matcher.start(), matcher.end(),
	                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	                }
	            }
	        }
	    }
	    
	    return hasChanges;
	}

	public static Spannable getSmiledText(Context context, CharSequence text) {
	    Spannable spannable = spannableFactory.newSpannable(text);
	    addSmiles(context, spannable);
	    return spannable;
	}
}
