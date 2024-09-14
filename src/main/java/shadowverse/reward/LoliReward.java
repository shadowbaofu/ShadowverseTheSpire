package shadowverse.reward;

import basemod.abstracts.CustomReward;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import shadowverse.cards.Nemesis.Condemned.Judith;
import shadowverse.characters.AbstractShadowversePlayer;

import java.util.*;

public class LoliReward extends CustomReward {
    public static final String ID = "shadowverse:LoliReward";

    public static final String[] TEXT = (CardCrawlGame.languagePack.getUIString("shadowverse:ClassReward")).TEXT;
    public CardGroup group;
    ArrayList<AbstractCard.CardTags> tags = new ArrayList<>(Arrays.asList(
            AbstractShadowversePlayer.Enums.CONDEMNED,
            AbstractShadowversePlayer.Enums.MACHINE,
            AbstractShadowversePlayer.Enums.CHESS,
            AbstractShadowversePlayer.Enums.ARTIFACT,
            AbstractShadowversePlayer.Enums.FES,
            AbstractShadowversePlayer.Enums.NATURAL,
            AbstractShadowversePlayer.Enums.HERO,
            AbstractShadowversePlayer.Enums.LEVIN,
            AbstractShadowversePlayer.Enums.MYSTERIA,
            AbstractShadowversePlayer.Enums.GILDED,
            AbstractShadowversePlayer.Enums.ACADEMIC,
            AbstractShadowversePlayer.Enums.ARMED));

    public LoliReward() {
        super(ImageMaster.loadImage("img/reward/placeholder.png"), TEXT[0], RewardType.CARD);
        this.group = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
        Map<AbstractCard.CardTags, Integer> counts = new HashMap<AbstractCard.CardTags, Integer>();
        for (AbstractCard.CardTags tag : tags) {
            counts.put(tag, 0);
        }
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if(c.rarity== AbstractCard.CardRarity.RARE){
                for (AbstractCard.CardTags t : c.tags) {
                    if (counts.containsKey(t)) {
                        counts.put(t, counts.get(t) + 1);
                    }
                }
            }
        }
        int maxValue = 0;
        ArrayList<AbstractCard.CardTags> maxKeys = new ArrayList<>();
        for (Integer value : counts.values()) {
            if (value > maxValue) {
                maxValue = value;
            }
        }
        for (AbstractCard.CardTags key : counts.keySet()) {
            if (counts.get(key) == maxValue) {
                maxKeys.add(key);
            }
        }
        if (maxKeys.size() > 0) {
            for (AbstractCard q : CardLibrary.getAllCards()) {
                for (AbstractCard.CardTags t : q.tags) {
                    if (maxKeys.contains(t)) {
                        this.group.addToBottom(q);
                        break;
                    }
                }
            }
            this.cards.clear();
            this.cards.addAll(getCards());
        } else {
            this.cards.clear();
            this.cards.add(new Judith());
        }
    }

    public ArrayList<AbstractCard> getCards() {
        if(this.group.size()<3){
            return this.group.group;
        }
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        while (cardsList.size() < 3) {
            AbstractCard card = this.group.getRandomCard(true, AbstractDungeon.getCurrRoom().getCardRarity(AbstractDungeon.cardRandomRng.random(100)));
            if (!cardListDuplicate(cardsList, card)) {
                cardsList.add(card);
            }
        }
        return cardsList;
    }

    public boolean cardListDuplicate(ArrayList<AbstractCard> cardsList, AbstractCard card) {
        for (AbstractCard alreadyHave : cardsList) {
            if (alreadyHave.cardID.equals(card.cardID)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean claimReward() {
        if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.COMBAT_REWARD) {
            AbstractDungeon.cardRewardScreen.open(this.cards, this, TEXT[0]);
            AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.COMBAT_REWARD;
        }
        return false;
    }
}

