package com.gutotech.youtube.model;

public class Item {
    public Id id;
    public Snippet snippet;

    public class Id {
        public String kind;
        public String videoId;
    }

    public class Snippet {
        public String publishedAt;
        public String channelId;
        public String title;
        public String description;

        public String channelTitle;
        public String liveBroadcastContent;

        public Thumbnails thumbnails;

        public class Thumbnails {
            public Default defaultThumbnail;
            public Medium medium;
            public High high;

            public class Default {
                public String url;
            }

            public class Medium {
                public String url;
            }

            public class High {
                public String url;
            }
        }
    }
}
