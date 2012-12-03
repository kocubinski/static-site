;;(doctype :xhtml-transitional)
[:html
 {:xmlns "http://www.w3.org/1999/xhtml", :lang "en", :xml:lang "en"}
 [:head
  [:meta
   {:http-equiv "content-type", :content "text/html; charset=UTF-8"}]
  [:meta {:name "description", :content (:description metadata)}]
  [:meta {:name "keywords", :content (:tags metadata)}]
  [:meta {:name "author", :content "Matt Kocubinski"}]
  [:link {:rel "icon", 
	  :href "/images/favicon.ico" :type "image/x-icon"}]
  [:link {:rel "shortcut icon", 
	  :href "/images/favicon.ico" :type "image/x-icon"}]
  [:link {:rel "stylesheet", :type "text/css", :href "/bootswatch.css"}]
  [:link
   {:rel "alternate", :type "application/rss+xml",
    :title (:site-title (static.config/config)), :href "/rss-feed"}]

  (if (= (:type metadata) :post)
    [:link {:rel "canonical" 
	    :href (str "http://pinekoan.com" (:url metadata))}])

  [:script {:src "http://cdn.mathjax.org/mathjax/latest/MathJax.js?config=TeX-AMS-MML_HTMLorMML",
            :type "text/javascript"}]
  [:script {:src "/highlight.pack.js", :type "text/javascript"}]
  [:script {:src "/jquery.js", :type "text/javascript"}]
  [:script {:type "text/javascript"}
   "$(document).ready(function() {
        $('pre code').each(function(i, e) {hljs.highlightBlock(e, '    ')});
        hljs.initHighlightingOnLoad();
    });"]
  [:title (:title metadata)]]
 [:body
  "<script type=\"text/javascript\">
  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-87333-8']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    (document.getElementsByTagName('head')[0] || document.getElementsByTagName('body')[0]).appendChild(ga);
  })();

</script>"
  [:div
   {:id "wrap"}
   [:div
    {:id "header"}
    [:h1 [:a {:href "/"} "pinekoan"]]
    [:div
     {:class "pages"}
     [:a {:href "/", :class "page"} "Home"] " | "
     [:a {:href "/projects.html", :class "page"} "Projects"] " | "
     [:a {:href "/archives.html", :class "page"} "Archives"] " | "
     [:a {:href "/tags/", :class "page"} "Tags"] " | "
     [:a {:href "/contact.html", :class "page" :rel "author"} "About"]

     [:form {:method "get" 
	     :action "http://www.google.com/search" :id "searchform"}
      [:div
       [:input {:type "text" :name "q" :class "box" :id "s"}]
       [:input {:type "hidden" :name "sitesearch"
		:value "nakkaya.com"}]]]]]
   [:div
    {:id "content"}
    [:div
     {:id "post"}
     (if (or (= (:type metadata) :post)
	     (= (:type metadata) :site)) 
       [:h2 (:title metadata)])

     content

     (if (= (:type metadata) :post)
       (reduce 
     	(fn[h v]
     	  (conj h [:a {:href (str "/tags/#" v)} (str v " ")]))
     	[:div {:class "post-tags"} "Tags: "] 
     	(.split (:tags metadata) " ")))]

    (if (= (:type metadata) :post)
      [:div
       {:id "related"}
       [:h3 "Random Posts"]
       [:ul
    	{:class "posts"}
	(map 
	 #(let [f %
		url (static.core/post-url f)
		[metadata _] (static.io/read-doc f)
		date (static.core/parse-date 
		      "yyyy-MM-dd" "dd MMM yyyy" 
		      (re-find #"\d*-\d*-\d*" (str f)))]
	   [:li [:span date] [:a {:href url} (:title metadata)]]) 
	 (take 5 (shuffle (static.io/list-files :posts))))]])

    [:div {:id "disqus"} 
     (if (= (:type metadata) :post) 
       "<div id=\"disqus_thread\"></div><script type=\"text/javascript\" src=\"http://disqus.com/forums/nakkaya/embed.js\"></script><noscript><a href=\"http://disqus.com/forums/nakkaya/?url=ref\">View the discussion thread.</a></noscript><a href=\"http://disqus.com\" class=\"dsq-brlink\">blog comments powered by <span class=\"logo-disqus\">Disqus</span></a>")]]
   [:div
    {:id "footer"}
    [:a {:href "/rss-feed"} " RSS Feed"]
    [:p "&copy; 2012" 
     [:a {:href "http://pinekoan.com"} " Matt Koc"]]]]
  ;;
  ;;
  (if (= (:type metadata) :post) 
    "<script type=\"text/javascript\">
//<![CDATA[
(function() {
	     var links = document.getElementsByTagName('a');
	     var query = '?';
	     for(var i = 0; i < links.length; i++) {
		     if(links[i].href.indexOf('#disqus_thread') >= 0) {
								       query += 'url' + i + '=' + encodeURIComponent(links[i].href) + '&';
								       }
		     }
	     document.write('<script charset=\"utf-8\" type=\"text/javascript\" src=\"http://disqus.com/forums/nakkaya/get_num_replies.js' + query + '\"></' + 'script>');
	     })();
//]]>
</script>")]]
