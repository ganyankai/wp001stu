//package cn.dante.book.lang;
//
//public class RuntimeEnvironment {
//    private static final String BC_PROVIDER_CLASS_NAME = "org.bouncycastle.jce.provider.BouncyCastleProvider";
//    private static final AtomicBoolean bcLoaded = new AtomicBoolean(false);
//    public static final boolean BOUNCY_CASTLE_AVAILABLE = Classes.isAvailable("org.bouncycastle.jce.provider.BouncyCastleProvider");
//
//    public RuntimeEnvironment() {
//    }
//
//    public static void enableBouncyCastleIfPossible() {
//        if (!bcLoaded.get()) {
//            try {
//                Class clazz = Classes.forName("org.bouncycastle.jce.provider.BouncyCastleProvider");
//                Provider[] providers = Security.getProviders();
//                Provider[] var2 = providers;
//                int var3 = providers.length;
//
//                for(int var4 = 0; var4 < var3; ++var4) {
//                    Provider provider = var2[var4];
//                    if (clazz.isInstance(provider)) {
//                        bcLoaded.set(true);
//                        return;
//                    }
//                }
//
//                Security.addProvider((Provider)Classes.newInstance(clazz));
//                bcLoaded.set(true);
//            } catch (UnknownClassException var6) {
//                ;
//            }
//
//        }
//    }
//
//    static {
//        enableBouncyCastleIfPossible();
//    }
//}