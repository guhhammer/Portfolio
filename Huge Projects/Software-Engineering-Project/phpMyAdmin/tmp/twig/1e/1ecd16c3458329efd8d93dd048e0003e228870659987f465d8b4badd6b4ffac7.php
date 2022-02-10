<?php

use Twig\Environment;
use Twig\Error\LoaderError;
use Twig\Error\RuntimeError;
use Twig\Extension\SandboxExtension;
use Twig\Markup;
use Twig\Sandbox\SecurityError;
use Twig\Sandbox\SecurityNotAllowedTagError;
use Twig\Sandbox\SecurityNotAllowedFilterError;
use Twig\Sandbox\SecurityNotAllowedFunctionError;
use Twig\Source;
use Twig\Template;

/* preferences/two_factor/main.twig */
class __TwigTemplate_1eb5764d53d7f0641280274e5ccade96eaf2333f9cfa665f66fb35747507a390 extends \Twig\Template
{
    private $source;
    private $macros = [];

    public function __construct(Environment $env)
    {
        parent::__construct($env);

        $this->source = $this->getSourceContext();

        $this->parent = false;

        $this->blocks = [
        ];
    }

    protected function doDisplay(array $context, array $blocks = [])
    {
        $macros = $this->macros;
        // line 1
        echo "<div class=\"group\">
  <h2>
    ";
        // line 3
        echo _gettext("Two-factor authentication status");
        // line 4
        echo "    ";
        echo PhpMyAdmin\Util::showDocu("two_factor");
        echo "
  </h2>
  <div class=\"group-cnt\">
    ";
        // line 7
        if (($context["enabled"] ?? null)) {
            // line 8
            echo "      ";
            if ((($context["num_backends"] ?? null) == 0)) {
                // line 9
                echo "        <p>";
                echo _gettext("Two-factor authentication is not available, please install optional dependencies to enable authentication backends.");
                echo "</p>
        <p>";
                // line 10
                echo _gettext("Following composer packages are missing:");
                echo "</p>
        <ul>
          ";
                // line 12
                $context['_parent'] = $context;
                $context['_seq'] = twig_ensure_traversable(($context["missing"] ?? null));
                foreach ($context['_seq'] as $context["_key"] => $context["item"]) {
                    // line 13
                    echo "            <li><code>";
                    echo twig_escape_filter($this->env, twig_get_attribute($this->env, $this->source, $context["item"], "dep", [], "any", false, false, false, 13), "html", null, true);
                    echo "</code> (";
                    echo twig_escape_filter($this->env, twig_get_attribute($this->env, $this->source, $context["item"], "class", [], "any", false, false, false, 13), "html", null, true);
                    echo ")</li>
          ";
                }
                $_parent = $context['_parent'];
                unset($context['_seq'], $context['_iterated'], $context['_key'], $context['item'], $context['_parent'], $context['loop']);
                $context = array_intersect_key($context, $_parent) + $_parent;
                // line 15
                echo "        </ul>
      ";
            } else {
                // line 17
                echo "        ";
                if (($context["backend_id"] ?? null)) {
                    // line 18
                    echo "          <p>";
                    echo _gettext("Two-factor authentication is available and configured for this account.");
                    echo "</p>
        ";
                } else {
                    // line 20
                    echo "          <p>";
                    echo _gettext("Two-factor authentication is available, but not configured for this account.");
                    echo "</p>
        ";
                }
                // line 22
                echo "      ";
            }
            // line 23
            echo "    ";
        } else {
            // line 24
            echo "      <p>";
            echo _gettext("Two-factor authentication is not available, enable phpMyAdmin configuration storage to use it.");
            echo "</p>
    ";
        }
        // line 26
        echo "  </div>
</div>

";
        // line 29
        if (($context["backend_id"] ?? null)) {
            // line 30
            echo "  <div class=\"group\">
    <h2>";
            // line 31
            echo twig_escape_filter($this->env, ($context["backend_name"] ?? null), "html", null, true);
            echo "</h2>
    <div class=\"group-cnt\">
      <p>";
            // line 33
            echo _gettext("You have enabled two factor authentication.");
            echo "</p>
      <p>";
            // line 34
            echo twig_escape_filter($this->env, ($context["backend_description"] ?? null), "html", null, true);
            echo "</p>
      <form method=\"post\" action=\"prefs_twofactor.php\">
        ";
            // line 36
            echo PhpMyAdmin\Url::getHiddenInputs();
            echo "
        <input class=\"btn btn-secondary\" type=\"submit\" name=\"2fa_remove\" value=\"";
            // line 38
            echo _gettext("Disable two-factor authentication");
            echo "\">
      </form>
    </div>
  </div>
";
        } elseif ((        // line 42
($context["num_backends"] ?? null) > 0)) {
            // line 43
            echo "  <div class=\"group\">
    <h2>";
            // line 44
            echo _gettext("Configure two-factor authentication");
            echo "</h2>
    <div class=\"group-cnt\">
      <form method=\"post\" action=\"prefs_twofactor.php\">
        ";
            // line 47
            echo PhpMyAdmin\Url::getHiddenInputs();
            echo "
        ";
            // line 48
            $context['_parent'] = $context;
            $context['_seq'] = twig_ensure_traversable(($context["backends"] ?? null));
            foreach ($context['_seq'] as $context["_key"] => $context["backend"]) {
                // line 49
                echo "          <label class=\"displayblock\">
            <input type=\"radio\" name=\"2fa_configure\" value=\"";
                // line 50
                echo twig_escape_filter($this->env, (($__internal_f607aeef2c31a95a7bf963452dff024ffaeb6aafbe4603f9ca3bec57be8633f4 = $context["backend"]) && is_array($__internal_f607aeef2c31a95a7bf963452dff024ffaeb6aafbe4603f9ca3bec57be8633f4) || $__internal_f607aeef2c31a95a7bf963452dff024ffaeb6aafbe4603f9ca3bec57be8633f4 instanceof ArrayAccess ? ($__internal_f607aeef2c31a95a7bf963452dff024ffaeb6aafbe4603f9ca3bec57be8633f4["id"] ?? null) : null), "html", null, true);
                echo "\"";
                // line 51
                echo ((((($__internal_62824350bc4502ee19dbc2e99fc6bdd3bd90e7d8dd6e72f42c35efd048542144 = $context["backend"]) && is_array($__internal_62824350bc4502ee19dbc2e99fc6bdd3bd90e7d8dd6e72f42c35efd048542144) || $__internal_62824350bc4502ee19dbc2e99fc6bdd3bd90e7d8dd6e72f42c35efd048542144 instanceof ArrayAccess ? ($__internal_62824350bc4502ee19dbc2e99fc6bdd3bd90e7d8dd6e72f42c35efd048542144["id"] ?? null) : null) == "")) ? (" checked") : (""));
                echo ">
            <strong>";
                // line 52
                echo twig_escape_filter($this->env, (($__internal_1cfccaec8dd2e8578ccb026fbe7f2e7e29ac2ed5deb976639c5fc99a6ea8583b = $context["backend"]) && is_array($__internal_1cfccaec8dd2e8578ccb026fbe7f2e7e29ac2ed5deb976639c5fc99a6ea8583b) || $__internal_1cfccaec8dd2e8578ccb026fbe7f2e7e29ac2ed5deb976639c5fc99a6ea8583b instanceof ArrayAccess ? ($__internal_1cfccaec8dd2e8578ccb026fbe7f2e7e29ac2ed5deb976639c5fc99a6ea8583b["name"] ?? null) : null), "html", null, true);
                echo "</strong>
            <p>";
                // line 53
                echo twig_escape_filter($this->env, (($__internal_68aa442c1d43d3410ea8f958ba9090f3eaa9a76f8de8fc9be4d6c7389ba28002 = $context["backend"]) && is_array($__internal_68aa442c1d43d3410ea8f958ba9090f3eaa9a76f8de8fc9be4d6c7389ba28002) || $__internal_68aa442c1d43d3410ea8f958ba9090f3eaa9a76f8de8fc9be4d6c7389ba28002 instanceof ArrayAccess ? ($__internal_68aa442c1d43d3410ea8f958ba9090f3eaa9a76f8de8fc9be4d6c7389ba28002["description"] ?? null) : null), "html", null, true);
                echo "</p>
          </label>
        ";
            }
            $_parent = $context['_parent'];
            unset($context['_seq'], $context['_iterated'], $context['_key'], $context['backend'], $context['_parent'], $context['loop']);
            $context = array_intersect_key($context, $_parent) + $_parent;
            // line 56
            echo "        <input class=\"btn btn-secondary\" type=\"submit\" value=\"";
            echo _gettext("Configure two-factor authentication");
            echo "\">
      </form>
    </div>
  </div>
";
        }
    }

    public function getTemplateName()
    {
        return "preferences/two_factor/main.twig";
    }

    public function isTraitable()
    {
        return false;
    }

    public function getDebugInfo()
    {
        return array (  188 => 56,  179 => 53,  175 => 52,  171 => 51,  168 => 50,  165 => 49,  161 => 48,  157 => 47,  151 => 44,  148 => 43,  146 => 42,  139 => 38,  135 => 36,  130 => 34,  126 => 33,  121 => 31,  118 => 30,  116 => 29,  111 => 26,  105 => 24,  102 => 23,  99 => 22,  93 => 20,  87 => 18,  84 => 17,  80 => 15,  69 => 13,  65 => 12,  60 => 10,  55 => 9,  52 => 8,  50 => 7,  43 => 4,  41 => 3,  37 => 1,);
    }

    public function getSourceContext()
    {
        return new Source("", "preferences/two_factor/main.twig", "C:\\xampp\\phpMyAdmin\\templates\\preferences\\two_factor\\main.twig");
    }
}
