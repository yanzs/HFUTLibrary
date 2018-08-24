function()
{
    var imgs = document.getElementsByTagName("img");
    for(var i = 0; i < imgs.length; i++)
    {
        imgs[i].onclick = function()
        {
            imgActivity.startImgActivity(this.src);
        }
    }

}