package com.alphawallet.token.entity;

import com.alphawallet.ethereum.EthereumNetworkBase;
import com.alphawallet.ethereum.NetworkInfo;

import static com.alphawallet.ethereum.EthereumNetworkBase.CLASSIC_ID;

import static com.alphawallet.ethereum.EthereumNetworkBase.MAINNET_ID;


/**
 * Created by James on 2/03/2019.
 * Stormbird in Singapore
 */
public class MagicLinkInfo
{
    //domains for DMZ
    public static final String mainnetMagicLinkDomain = "aw.app";
    private static final String legacyMagicLinkDomain = "app.awallet.io";
    private static final String classicMagicLinkDomain = "classic.aw.app";

    //Etherscan domains
    private static final String mainNetEtherscan = "https://cn.etherscan.com/";
    private static final String classicEtherscan = "https://blockscout.com/etc/mainnet/";

    //TODO: determine callisto etherscan

    public static String getNetworkNameById(long networkId) {
        NetworkInfo info = EthereumNetworkBase.getNetworkByChain(networkId);
        if (info != null)
        {
            return info.name;
        }
        else
        {
            return EthereumNetworkBase.getNetworkByChain(MAINNET_ID).name;
        }
    }

    public static String getMagicLinkDomainFromNetworkId(long networkId) {
        switch ((int)networkId) {
            case 0:
                return legacyMagicLinkDomain;
            case (int)MAINNET_ID:
            default:
                return mainnetMagicLinkDomain;
            case (int)CLASSIC_ID:
                return classicMagicLinkDomain;
        }
    }

    //For testing you will not have the correct domain (localhost)
    //To test, alter the else statement to return the network you wish to test
    public static long getNetworkIdFromDomain(String domain) {
        switch(domain) {
            case mainnetMagicLinkDomain:
            default:
                return MAINNET_ID;
            case legacyMagicLinkDomain:
                return MAINNET_ID;
            case classicMagicLinkDomain:
                return CLASSIC_ID;

        }
    }


    public static long identifyChainId(String link)
    {
        if (link == null || link.length() == 0) return 0;

        long chainId = 0;
        //split out the chainId from the magiclink
        int index = link.indexOf(mainnetMagicLinkDomain);
        int dSlash = link.indexOf("://");
        int legacy = link.indexOf(legacyMagicLinkDomain);
        //try new style link
        if (index > 0 && dSlash > 0)
        {
            String domain = link.substring(dSlash+3, index + mainnetMagicLinkDomain.length());
            chainId = getNetworkIdFromDomain(domain);
        }
        else if (legacy > 0)
        {
            chainId = 0;
        }

        return chainId;
    }

    public static String generatePrefix(long chainId)
    {
        return "https://" + getMagicLinkDomainFromNetworkId(chainId) + "/";
    }
}
