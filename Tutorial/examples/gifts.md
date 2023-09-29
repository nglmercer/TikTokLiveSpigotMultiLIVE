# How to handle Gifts?



```yaml
onGift:
   /title @p title "&7Thank you for ${event.comboCount} &2${event.gift.name}"
                               
   switch event.gift.name 
    case "ItCorn"              
    then /say Thank you for ${event.comboCount} Corns      
   
    case "Rose"                 
    then /say Thank you for Rose
  
    case "Bravo"                
    then /say Thank you for Bravo
  
    case "TikTok"               
    then /say Thank you for TikTok
  
    default                    
    then /say this is some undefined gift!
```
